package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.request.dto.ReqItemRequestDto;
import ru.practicum.shareit.request.dto.RespGetItemRequestsDto;
import ru.practicum.shareit.request.dto.RespItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

import static ru.practicum.shareit.constant.message.ItemRequestValidMessages.ITEM_REQUEST_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserService userService;

    @Override
    public RespItemRequestDto createItemRequest(ReqItemRequestDto itemRequest, Long userId) {
        User user = UserMapper.toUser(userService.getUserById(userId));
        ItemRequest createdItemRequest = itemRequestRepository.save(ItemRequestMapper.toItemRequest(itemRequest, user));
        return ItemRequestMapper.toRespItemRequestDto(createdItemRequest);
    }

    @Override
    public Collection<RespItemRequestDto> getItemRequests(Long userId) {
        return itemRequestRepository.findByRequestorIdNotOrderByCreatedDesc(userId)
                .stream()
                .map(ItemRequestMapper::toRespItemRequestDto)
                .toList();
    }

    @Override
    public Collection<RespGetItemRequestsDto> getUserItemRequests(Long userId) {
        return itemRequestRepository.findUserRequests(userId)
                .stream()
                .map(ItemRequestMapper::toRespGetItemRequestsDto)
                .toList();
    }

    @Override
    public RespGetItemRequestsDto getItemRequestById(Long requestId) {
        return itemRequestRepository.findById(requestId)
                .map(ItemRequestMapper::toRespGetItemRequestsDto)
                .orElseThrow(() -> new NotFoundException(String.format(ITEM_REQUEST_NOT_FOUND_MESSAGE, requestId)));
    }
}
