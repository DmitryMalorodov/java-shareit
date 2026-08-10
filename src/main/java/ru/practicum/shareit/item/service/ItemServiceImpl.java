package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.AccessDeniedException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.shareit.constant.message.ItemValidMessages.*;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRequestService itemRequestService;

    @Override
    public GetUserItemsDto getItemById(Long id, Long userId) {
        GetUserItemsDto item = itemRepository.findById(id)
                .map(ItemMapper::toGetUserItemsDto)
                .orElseThrow(() -> new NotFoundException(String.format(ITEM_NOT_FOUND_MESSAGE, id)));

        setCommentsToItems(List.of(item.getId()), List.of(item));
        if (item.getOwner().getId().equals(userId)) {
            setBookingDatesToItems(List.of(item.getId()), List.of(item));
        }

        return item;
    }

    @Override
    public Collection<GetUserItemsDto> getUserItems(Long userId) {
        //получение вещей пользователя
        List<GetUserItemsDto> items = itemRepository.findByOwnerId(userId)
                .stream()
                .map(ItemMapper::toGetUserItemsDto)
                .toList();

        if (items.isEmpty()) return List.of();

        //сбор всех id вещей в список
        List<Long> itemIds = items.stream().map(GetUserItemsDto::getId).toList();

        //установка комментов и дат прошедших/ближайших бронирований
        setCommentsToItems(itemIds, items);
        setBookingDatesToItems(itemIds, items);

        return items;
    }

    @Override
    public RespItemDto create(ReqItemDto item, Long userId) {
        User user = UserMapper.toUser(userService.getUserById(userId));
        ItemRequest itemRequest = item.getRequestId() != null
                ? ItemRequestMapper.toItemRequest(itemRequestService.getItemRequestById(item.getRequestId()))
                : null;
        Item createdItem = itemRepository.save(ItemMapper.toItem(item, user, itemRequest));
        return ItemMapper.toItemDto(createdItem);
    }

    @Override
    public RespItemDto update(ReqItemDto newItem, Long userId, Long itemId) {
        GetUserItemsDto item = getItemById(itemId, userId);
        Item oldItem = ItemMapper.toItem(item);
        if (!oldItem.getOwner().getId().equals(userId))
            throw new AccessDeniedException(ITEM_UPDATE_ACCESS_MESSAGE);

        if (newItem.getName() != null && !newItem.getName().isBlank()) oldItem.setName(newItem.getName());
        if (newItem.getDescription() != null && !newItem.getDescription().isBlank()) oldItem.setDescription(newItem.getDescription());
        if (newItem.getAvailable() != null) oldItem.setAvailable(newItem.getAvailable());
        oldItem = itemRepository.save(oldItem);

        return ItemMapper.toItemDto(oldItem);
    }

    @Override
    public Collection<RespItemDto> search(String text) {
        if (text.isBlank()) return List.of();

        return itemRepository.search(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public RespCommentDto create(ReqCommentDto comment, Long userId, Long itemId) {
        //фильтруем брониования по userId и оставляем только те, которые уже завершились
        Booking itemBooking = bookingRepository.findByItemId(itemId)
                .stream()
                .filter(booking -> booking.getBooker().getId().equals(userId))
                .filter(booking -> booking.getEnd().isBefore(LocalDateTime.now()))
                .findFirst()
                .orElse(null);

        if (itemBooking == null) {
            throw new ValidationException(COMMENT_ACCESS_MESSAGE);
        }

        Comment createdComment = commentRepository.save(CommentMapper.toComment(
                comment, itemBooking.getItem(), itemBooking.getBooker()));
        return CommentMapper.toRespCommentDto(createdComment);
    }

    private void setCommentsToItems(List<Long> itemIds, List<GetUserItemsDto> items) {
        //забираем из БД все комменты по itemIds и группируем по itemId
        Map<Long, List<Comment>> commentsMap = commentRepository.findByItemIdIn(itemIds)
                .stream()
                .collect(Collectors.groupingBy(comment -> comment.getItem().getId()));

        //установка комментов в дто
        for (GetUserItemsDto item : items) {
            List<Comment> itemComments = commentsMap.getOrDefault(item.getId(), List.of());
            item.setComments(CommentMapper.toRespCommentDto(itemComments));
        }
    }

    private void setBookingDatesToItems(List<Long> itemIds, List<GetUserItemsDto> items) {
        //забираем из БД все брони по itemIds и группируем по itemId
        Map<Long, List<Booking>> bookingsMap = bookingRepository.findByItemIdInAndStatus(itemIds, BookingStatus.APPROVED)
                .stream()
                .collect(Collectors.groupingBy(booking -> booking.getItem().getId()));

        //установка информации по бронированию в дто
        for (GetUserItemsDto item : items) {
            List<Booking> itemBookings = bookingsMap.getOrDefault(item.getId(), List.of());
            setNextAndLastBookingToItem(item, itemBookings);
        }
    }

    private void setNextAndLastBookingToItem(GetUserItemsDto item, List<Booking> itemBookings) {
        if (itemBookings.isEmpty()) return;

        //получение ближайшего бронирования для item
        Booking nextBooking = itemBookings.stream()
                .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                .min(Comparator.comparing(Booking::getStart))
                .orElse(null);

        //получение самого последнего бронирования для item
        Booking lastBooking = itemBookings.stream()
                .filter(booking -> !booking.getStart().isAfter(LocalDateTime.now()))
                .max(Comparator.comparing(Booking::getStart))
                .orElse(null);

        //устанавливаем даты ближайшего бронирования для передачи в dto
        if (nextBooking != null) {
            item.setNextBooking(NextBookingDateDto.builder()
                    .start(nextBooking.getStart())
                    .end(nextBooking.getEnd())
                    .build());
        }

        //устанавливаем даты самого последнего бронирования для передачи в dto
        if (lastBooking != null) {
            item.setLastBooking(LastBookingDateDto.builder()
                    .start(lastBooking.getStart())
                    .end(lastBooking.getEnd())
                    .build());
        }
    }
}
