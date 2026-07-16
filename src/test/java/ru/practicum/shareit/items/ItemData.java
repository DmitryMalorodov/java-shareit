package ru.practicum.shareit.items;

import ru.practicum.shareit.item.dto.ItemDto;

public class ItemData {
    public static final ItemDto item = ItemDto.builder()
            .name("Имя_111")
            .description("desc")
            .available(true)
            .build();

    public static final ItemDto item2 = ItemDto.builder()
            .name("nam1")
            .description("desc333")
            .available(false)
            .build();

    public static final ItemDto item3 = ItemDto.builder()
            .name("name")
            .description("de111")
            .available(true)
            .build();
}
