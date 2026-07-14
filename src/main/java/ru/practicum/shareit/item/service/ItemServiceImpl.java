package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.AccessDeniedException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    @Override
    public ItemDto getItemById(Long id) {
        return itemRepository.getItemById(id)
                .map(ItemMapper::toItemDto)
                .orElseThrow(() -> new NotFoundException("Вещь не найдена с id: "  + id));
    }

    @Override
    public Collection<ItemDto> getUserItems(Long userId) {
        return itemRepository.getUserItems(userId)
                .stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public ItemDto create(ItemDto item, Long userId) {
        userService.getUserById(userId);
        Item createdItem = itemRepository.create(ItemMapper.toItem(item, userId));
        return ItemMapper.toItemDto(createdItem);
    }

    @Override
    public ItemDto update(ItemDto newItem, Long userId, Long itemId) {
        ItemDto item = getItemById(itemId);
        Item oldItem = ItemMapper.toItem(item, item.getOwnerId());
        if (!oldItem.getOwnerId().equals(userId))
            throw new AccessDeniedException("Вещь может редактировать только ее собственник");

        if (newItem.getName() != null && !newItem.getName().isBlank()) oldItem.setName(newItem.getName());
        if (newItem.getDescription() != null && !newItem.getDescription().isBlank()) oldItem.setDescription(newItem.getDescription());
        if (newItem.getAvailable() != null) oldItem.setAvailable(newItem.getAvailable());
        oldItem = itemRepository.update(oldItem);

        return ItemMapper.toItemDto(oldItem);
    }

    @Override
    public Collection<ItemDto> search(String text) {
        if (text.isBlank()) return List.of();

        return itemRepository.search(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }
}
