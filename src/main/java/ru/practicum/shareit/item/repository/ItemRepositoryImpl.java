package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(0L);

    @Override
    public Optional<Item> getItemById(Long id) {
        Item item = items.get(id);
        return item != null ? Optional.of(item) : Optional.empty();
    }

    @Override
    public Collection<Item> getUserItems(Long userId) {
        return items.values().stream()
                .filter(item -> item.getOwnerId().equals(userId))
                .toList();
    }

    @Override
    public Item create(Item item) {
        Long id = counter.incrementAndGet();
        item.setId(id);
        items.put(id, item);
        return item;
    }

    @Override
    public Item update(Item newItem) {
        Item oldItem = items.get(newItem.getId());
        oldItem.setName(newItem.getName());
        oldItem.setDescription(newItem.getDescription());
        oldItem.setAvailable(newItem.getAvailable());
        return oldItem;
    }

    @Override
    public Collection<Item> search(String text) {
        String textForSearch = text.toLowerCase();
        return items.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(textForSearch)
                        || item.getDescription().toLowerCase().contains(textForSearch))
                .filter(item -> item.getAvailable() == true)
                .toList();
    }
}
