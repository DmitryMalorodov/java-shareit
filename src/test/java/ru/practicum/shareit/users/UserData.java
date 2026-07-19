package ru.practicum.shareit.users;

import ru.practicum.shareit.user.dto.ReqUserDto;

public class UserData {
    public static final ReqUserDto user = ReqUserDto.builder()
            .name("Имя")
            .email("email@em.ru")
            .build();

    public static final ReqUserDto user2 = ReqUserDto.builder()
            .name("Имя2")
            .email("email111@em.ru")
            .build();
}
