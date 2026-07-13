package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    UserDto getUserById(Long id);
    Collection<UserDto> getUsers();
    UserDto create(UserDto user);
    UserDto update(UserDto user);
    void deleteUser(Long id);
}
