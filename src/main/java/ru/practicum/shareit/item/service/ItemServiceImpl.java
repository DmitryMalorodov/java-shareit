package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.AccessDeniedException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ReqItemDto;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.List;

import static ru.practicum.shareit.constant.message.ItemValidMessages.ITEM_NOT_FOUND_MESSAGE;
import static ru.practicum.shareit.constant.message.ItemValidMessages.ITEM_UPDATE_ACCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemRepository itemRepository;

    @Override
    public RespItemDto getItemById(Long id) {
        return itemRepository.findById(id)
                .map(ItemMapper::toItemDto)
                .orElseThrow(() -> new NotFoundException(String.format(ITEM_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public Collection<RespItemDto> getUserItems(Long userId) {
        return itemRepository.findByOwnerId(userId)
                .stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public RespItemDto create(ReqItemDto item, Long userId) {
        User user = UserMapper.toUser(userService.getUserById(userId));
        Item createdItem = itemRepository.save(ItemMapper.toItem(item, user));
        return ItemMapper.toItemDto(createdItem);
    }

    @Override
    public RespItemDto update(ReqItemDto newItem, Long userId, Long itemId) {
        RespItemDto item = getItemById(itemId);
        Item oldItem = ItemMapper. toItem(item);
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
}
