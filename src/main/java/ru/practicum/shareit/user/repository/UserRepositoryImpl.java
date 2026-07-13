package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Map<Long, User> users = new HashMap<>();
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
        Long id = counter.incrementAndGet();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @Override
    public User update(User newUser) {
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
