package ru.practicum.shareit.users;

import ru.practicum.shareit.user.dto.ReqUserDto;

public class UserData {
    public static final ReqUserDto user = ReqUserDto.builder()
            .name("name")
            .email("email@em.ru")
            .build();
}
