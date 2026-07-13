package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserService {
    UserDto getUserById(Long id);
    Collection<UserDto> getUsers();
    UserDto create(User user);
    UserDto update(User user);
    void deleteUser(Long id);
}
