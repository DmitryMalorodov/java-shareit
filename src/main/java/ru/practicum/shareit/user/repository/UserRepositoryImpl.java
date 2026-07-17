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
    private final Set<String> registeredEmails = new HashSet<>();
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
        checkEmail(user);

        Long id = counter.incrementAndGet();
        user.setId(id);
        users.put(id, user);
        registeredEmails.add(user.getEmail());
        return user;
    }

    @Override
    public User update(User newUser) {
        User oldUser = users.get(newUser.getId());

        //если email изменился, то проверяем, что указанный email еще никем не занят
        if (!oldUser.getEmail().equals(newUser.getEmail())) {
            checkEmail(newUser);
            registeredEmails.remove(oldUser.getEmail());
            registeredEmails.add(newUser.getEmail());
        }

        oldUser.setName(newUser.getName());
        oldUser.setEmail(newUser.getEmail());
        return newUser;
    }

    @Override
    public void deleteUser(Long id) {
        registeredEmails.remove(users.get(id).getEmail());
        users.remove(id);
    }

    private void checkEmail(User user) {
        if (registeredEmails.contains(user.getEmail()))
            throw new EmailExistsException(String.format(USER_ALREADY_EXISTS_WITH_EMAIL, user.getEmail()));
    }
}
