package ru.practicum.shareit.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.ReqUserDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка изменения пользователя")
public class UpdateUserTests extends UserTest {
    private Long userId;
    private final ReqUserDto newUser = prepareReqBody(user);

    @BeforeEach
    void setUp() throws Exception {
        userId = getIdFromObject(createUserResAct(user));
    }

    @Test
    void checkChangeUser() throws Exception {
        RespUserDto changedUser = changeUser(newUser, userId);
        checkUser(changedUser, newUser);
    }

    private ReqUserDto prepareReqBody(ReqUserDto user) {
        return user.toBuilder()
                .name("otherName")
                .email("other@email.ru")
                .build();
    }
}
