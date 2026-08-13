package ru.practicum.shareit.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.RespUserDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка добавления пользователя")
public class CreateUserTests extends UserTest {

    @Test
    void checkCreateUser() throws Exception {
        RespUserDto createdUser = createUser(user);
        checkUser(createdUser, user);
    }

    @Test
    void checkCreateUserWithExistedEmailValidation() throws Exception {
        createUserResAct(user);
        createUserResAct(user)
                .andExpect(status().isConflict());
    }
}
