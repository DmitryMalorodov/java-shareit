package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.ReqUserDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.Collection;

public interface UserService {

    RespUserDto getUserById(Long id);

    Collection<RespUserDto> getUsers();

    RespUserDto create(ReqUserDto user);

    RespUserDto update(ReqUserDto user, Long userId);

    void deleteUser(Long id);
}
