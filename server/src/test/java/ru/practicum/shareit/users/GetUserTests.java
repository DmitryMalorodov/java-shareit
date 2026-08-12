package ru.practicum.shareit.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.RespUserDto;

import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка получения пользователя")
public class GetUserTests extends UserTest {

    @Test
    void checkGetUser() throws Exception {
        Long userId = getIdFromObject(createUserResAct(user));
        RespUserDto actUser = getUser(userId);
        checkUser(actUser, user);
    }
}
