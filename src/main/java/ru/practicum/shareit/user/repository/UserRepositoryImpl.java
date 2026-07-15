package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.EmailExistsException;
import ru.practicum.shareit.user.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static ru.practicum.shareit.constant.message.UserValidationMessages.USER_ALREADY_EXISTS_WITH_EMAIL;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(0L);

    @Override
    public Optional<User> getUserById(Long id) {
        User user = users.get(id);
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User create(User user) {
        //проверяем, что указанный email еще никем не занят
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail())))
            throw new EmailExistsException(String.format(USER_ALREADY_EXISTS_WITH_EMAIL, user.getEmail()));

        Long id = counter.incrementAndGet();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @Override
    public User update(User newUser) {
        //проверяем, что указанный email еще никем не занят, кроме целевого юзера
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(newUser.getEmail())
        && !u.getId().equals(newUser.getId())))
            throw new EmailExistsException(String.format(USER_ALREADY_EXISTS_WITH_EMAIL, newUser.getEmail()));

        User oldUser = users.get(newUser.getId());
        oldUser.setName(newUser.getName());
        oldUser.setEmail(newUser.getEmail());
        return newUser;
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(id);
    }
}
