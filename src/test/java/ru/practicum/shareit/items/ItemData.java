package ru.practicum.shareit.items;

import ru.practicum.shareit.item.dto.ReqItemDto;

public class ItemData {
        public static final ReqItemDto item = ReqItemDto.builder()
            .name("Имя_111")
            .description("desc")
            .available(true)
            .build();

    public static final ReqItemDto item2 = ReqItemDto.builder()
            .name("nam1")
            .description("desc333")
            .available(false)
            .build();

    public static final ReqItemDto item3 = ReqItemDto.builder()
            .name("name")
            .description("de111")
            .available(true)
            .build();
}
