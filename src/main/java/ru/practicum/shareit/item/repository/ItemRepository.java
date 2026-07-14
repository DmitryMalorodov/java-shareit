package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Optional;

public interface ItemRepository {
    Optional<Item> getItemById(Long id);
    Collection<Item> getUserItems(Long userId);
    Item create(Item item);
    Item update(Item item);
    Collection<Item> search(String text);
}