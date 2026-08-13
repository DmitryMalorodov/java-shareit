package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.marker.OnCreate;
import ru.practicum.shareit.marker.OnUpdate;
import ru.practicum.shareit.user.dto.ReqUserDto;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserClient userClient;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable final Long id) {
        log.info("Get user by id={}", id);
        return userClient.getUserById(id);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        log.info("Get all users");
        return userClient.getUsers();
    }

    @PostMapping
    public ResponseEntity<Object> create(@Validated(OnCreate.class) @RequestBody final ReqUserDto user) {
        log.info("Create user {}", user);
        return userClient.createUser(user);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(
            @Validated(OnUpdate.class) @RequestBody final ReqUserDto newUser,
            @PathVariable final Long userId
    ) {
        log.info("Update user with new data={}, userId={}", newUser, userId);
        return userClient.updateUser(newUser, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable final Long id) {
        log.info("Delete user by id={}", id);
        return userClient.deleteUser(id);
    }
}
