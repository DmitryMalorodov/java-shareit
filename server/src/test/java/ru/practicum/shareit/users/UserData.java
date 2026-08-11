package ru.practicum.shareit.users;

import ru.practicum.shareit.user.dto.ReqUserDto;

public class UserData {
    public static final ReqUserDto user = ReqUserDto.builder()
            .name("name")
            .email("email@em.ru")
            .build();

    public static final ReqUserDto user2 = ReqUserDto.builder()
            .name("name2")
            .email("email111@em.ru")
            .build();

    public static final ReqUserDto user3 = ReqUserDto.builder()
            .name("name3")
            .email("email333@em.ru")
            .build();
}
