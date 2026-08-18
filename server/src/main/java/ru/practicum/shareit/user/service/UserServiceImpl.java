package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.ReqUserDto;
import ru.practicum.shareit.user.dto.RespUserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;

import static ru.practicum.shareit.constant.message.UserValidationMessages.USER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public RespUserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toUserDto)
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public Collection<RespUserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    @Override
    public RespUserDto create(ReqUserDto user) {
        User createdUser = userRepository.save(UserMapper.toUser(user));
        return UserMapper.toUserDto(createdUser);
    }

    @Override
    public RespUserDto update(ReqUserDto newUser, Long userId) {
        User oldUser = UserMapper.toUser(getUserById(userId));
        if (newUser.getName() != null && !newUser.getName().isBlank()) oldUser.setName(newUser.getName());
        if (newUser.getEmail() != null && !newUser.getEmail().isBlank()) oldUser.setEmail(newUser.getEmail());

        oldUser = userRepository.save(oldUser);
        return UserMapper.toUserDto(oldUser);
    }

    @Override
    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }
}
