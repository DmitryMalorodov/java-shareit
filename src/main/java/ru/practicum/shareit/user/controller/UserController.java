package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.marker.OnCreate;
import ru.practicum.shareit.marker.OnUpdate;
import ru.practicum.shareit.user.dto.ReqUserDto;
import ru.practicum.shareit.user.dto.RespUserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public RespUserDto getUserById(@PathVariable final Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public Collection<RespUserDto> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public RespUserDto create(@Validated(OnCreate.class) @RequestBody final ReqUserDto user) {
        return userService.create(user);
    }

    @PatchMapping("/{userId}")
    public RespUserDto update(
            @Validated(OnUpdate.class) @RequestBody final ReqUserDto newUser,
            @PathVariable final Long userId
    ) {
        return userService.update(newUser, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable final Long id) {
        userService.deleteUser(id);
    }
}
