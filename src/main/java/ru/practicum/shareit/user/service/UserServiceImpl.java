package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.getUserById(id)
                .map(UserMapper::toUserDto)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден с id: "  + id));
    }

    @Override
    public Collection<UserDto> getUsers() {
        return userRepository.getUsers()
                .stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto create(User user) {
        user = userRepository.create(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto update(User newUser) {
        User oldUser = UserMapper.toUser(getUserById(newUser.getId()));
        if (newUser.getName() != null && !newUser.getName().isBlank()) oldUser.setName(newUser.getName());
        if (newUser.getEmail() != null && !newUser.getEmail().isBlank()) oldUser.setEmail(newUser.getEmail());

        newUser = userRepository.update(oldUser);
        return UserMapper.toUserDto(newUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }
}
