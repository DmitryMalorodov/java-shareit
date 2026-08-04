package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.AccessDeniedException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

import static ru.practicum.shareit.booking.model.BookingStatus.*;
import static ru.practicum.shareit.constant.message.BookingValidMessages.*;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public RespBookingDto getBookingById(Long id, Long userId) {
        RespBookingDto booking = bookingRepository.findById(id)
                .map(BookingMapper::toRespBookingDto)
                .orElseThrow(() -> new NotFoundException(String.format(BOOKING_NOT_FOUND_MESSAGE, id)));

        if (!booking.getItem().getOwner().getId().equals(userId) && !booking.getBooker().getId().equals(userId)) {
            throw new AccessDeniedException(BOOKING_ACCESS_ERROR);
        }

        return booking;
    }

    @Override
    public RespBookingDto create(ReqBookingDto booking, Long userId) {
        Item item = ItemMapper.toItem(itemService.getItemById(booking.getItemId(), userId));
        User user = UserMapper.toUser(userService.getUserById(userId));

        if (!item.getAvailable()) {
            throw new ValidationException("Невозможно забронировать недоступную вещь!");
        }

        Booking createdBooking = bookingRepository.save(BookingMapper.toBooking(booking, item, user));
        return BookingMapper.toRespBookingDto(createdBooking);
    }

    @Override
    public RespBookingDto approveBooking(Long bookingId, Boolean approved, Long userId) {
        Booking booking = BookingMapper.toBooking(getBookingById(bookingId, userId));

        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new AccessDeniedException(BOOKING_APPROVED_ACCESS_ERROR);
        }

        if (booking.getStatus().equals(WAITING)) {
            if (approved == true) {
                booking.setStatus(APPROVED);
            } else {
                booking.setStatus(REJECTED);
            }
        } else {
            throw new AccessDeniedException("Подтверждать или отклонять бронирование можно только в статусе WAITING " +
                    "текущий статус бронирования - " + booking.getStatus());
        }

        Booking createdBooking = bookingRepository.save(booking);
        return BookingMapper.toRespBookingDto(createdBooking);
    }

    @Override
    public Collection<RespBookingDto> getUserBookings(Long userId, BookingState state) {
        return getBookingsByState(userId, state,
                bookingRepository::findByBookerIdOrderByStartDesc,
                (id, time) -> bookingRepository.findByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(id, time, time),
                bookingRepository::findByBookerIdAndEndIsBeforeOrderByStartDesc,
                bookingRepository::findByBookerIdAndStartIsAfterOrderByStartDesc,
                bookingRepository::findByBookerIdAndStatusOrderByStartDesc
        );
    }

    @Override
    public Collection<RespBookingDto> getUserItemsBookings(Long userId, BookingState state) {
        if (itemService.getUserItems(userId).isEmpty()) {
            throw new NotFoundException("У пользователя нет ни одной вещи!");
        }

        return getBookingsByState(userId, state,
                bookingRepository::findByItemOwnerIdOrderByStartDesc,
                (id, time) -> bookingRepository.findByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(id, time, time),
                bookingRepository::findByItemOwnerIdAndEndIsBeforeOrderByStartDesc,
                bookingRepository::findByItemOwnerIdAndStartIsAfterOrderByStartDesc,
                bookingRepository::findByItemOwnerIdAndStatusOrderByStartDesc
        );
    }

    private Collection<RespBookingDto> getBookingsByState(
            Long userId,
            BookingState state,
            Function<Long, Collection<Booking>> findAll,
            BookingFinder findCurrent,
            BookingFinder findPast,
            BookingFinder findFuture,
            BiFunction<Long, BookingStatus, Collection<Booking>> findByStatus
    ) {
        Collection<Booking> bookings = switch (state) {
            case ALL -> findAll.apply(userId);
            case CURRENT -> findCurrent.find(userId, LocalDateTime.now());
            case PAST -> findPast.find(userId, LocalDateTime.now());
            case FUTURE -> findFuture.find(userId, LocalDateTime.now());
            case WAITING -> findByStatus.apply(userId, BookingStatus.WAITING);
            case REJECTED -> findByStatus.apply(userId, BookingStatus.REJECTED);
        };

        return BookingMapper.toRespBookingDto(bookings);
    }
}
