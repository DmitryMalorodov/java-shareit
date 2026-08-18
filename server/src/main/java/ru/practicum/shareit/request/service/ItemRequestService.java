package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ReqItemRequestDto;
import ru.practicum.shareit.request.dto.RespGetItemRequestsDto;
import ru.practicum.shareit.request.dto.RespItemRequestDto;

import java.util.Collection;

public interface ItemRequestService {
    RespItemRequestDto createItemRequest(ReqItemRequestDto itemRequest, Long userId);

    //получение всех запросов (кроме запрсов юзера)
    Collection<RespItemRequestDto> getItemRequests(Long userId);

    //получение всех запросов юзера
    Collection<RespGetItemRequestsDto> getUserItemRequests(Long userId);

    RespGetItemRequestsDto getItemRequestById(Long requestId);
}
