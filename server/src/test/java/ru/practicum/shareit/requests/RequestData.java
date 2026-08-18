package ru.practicum.shareit.requests;

import ru.practicum.shareit.request.dto.ReqItemRequestDto;

public class RequestData {
    public static final ReqItemRequestDto request = ReqItemRequestDto.builder()
            .description("some description")
            .build();
}
