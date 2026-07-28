package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.GetUserItemsDto;
import ru.practicum.shareit.item.dto.ReqItemDto;
import ru.practicum.shareit.item.dto.RespItemDto;

import java.util.Collection;

public interface ItemService {

    RespItemDto getItemById(Long id);

    Collection<GetUserItemsDto> getUserItems(Long userId);

    RespItemDto create(ReqItemDto item, Long userId);

    RespItemDto update(ReqItemDto item, Long userId, Long itemId);

    Collection<RespItemDto> search(String text);
}
