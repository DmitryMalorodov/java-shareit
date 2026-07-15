package ru.practicum.shareit.users;

import ru.practicum.shareit.user.dto.UserDto;

public class UserData {
    public static final UserDto user = UserDto.builder()
            .name("Имя")
            .email("email@em.ru")
            .build();

    public static final UserDto user2 = UserDto.builder()
            .name("Имя2")
            .email("email111@em.ru")
            .build();
}
