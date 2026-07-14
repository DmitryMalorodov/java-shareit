package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

public interface ItemService {
    ItemDto getItemById(Long id);
    Collection<ItemDto> getUserItems(Long userId);
    ItemDto create(ItemDto item, Long userId);
    ItemDto update(ItemDto item, Long userId, Long itemId);
    Collection<ItemDto> search(String text);
}
