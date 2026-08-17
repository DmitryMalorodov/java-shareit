package ru.practicum.shareit.items;

import ru.practicum.shareit.item.dto.ReqCommentDto;
import ru.practicum.shareit.item.dto.ReqItemDto;

public class ItemData {
    public static final ReqItemDto item = ReqItemDto.builder()
            .name("me_111")
            .description("desc")
            .available(true)
            .build();

    public static final ReqCommentDto comment = ReqCommentDto.builder()
            .text(" ")
            .build();

    public static final ReqCommentDto comment2 = ReqCommentDto.builder()
            .text("text")
            .build();
}
