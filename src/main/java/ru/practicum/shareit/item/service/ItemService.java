package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

import java.util.Collection;

public interface ItemService {

    GetUserItemsDto getItemById(Long id);

    Collection<GetUserItemsDto> getUserItems(Long userId);

    RespItemDto create(ReqItemDto item, Long userId);

    RespItemDto update(ReqItemDto item, Long userId, Long itemId);

    Collection<RespItemDto> search(String text);

    RespCommentDto create(ReqCommentDto comment, Long userId, Long itemId);
}
