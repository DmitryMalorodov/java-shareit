package ru.practicum.shareit.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.ReqUserDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.message.UserValidationMessages.*;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка добавления пользователя")
public class CreateUserTests extends UserTest {

    @Test
    void checkCreateUser() throws Exception {
        RespUserDto createdUser = createUserDto(user);
        checkUser(createdUser, user);
    }

    @Test
    void checkEmailNullValidation() throws Exception {
        ReqUserDto userEmailNull = user.toBuilder().email(null).build();
        checkValidationError(createUser(userEmailNull), EMAIL_BLANK_MESSAGE);
    }

    @Test
    void checkEmailNotCorrectValidation() throws Exception {
        ReqUserDto userEmailNotCorrect = user.toBuilder().email("email").build();
        checkValidationError(createUser(userEmailNotCorrect), EMAIL_NOT_CORRECT_MESSAGE);
    }

    @Test
    void checkNameNullValidation() throws Exception {
        ReqUserDto userEmailNull = user.toBuilder().name(null).build();
        checkValidationError(createUser(userEmailNull), NAME_BLANK_MESSAGE);
    }

    @Test
    void checkNameBlankValidation() throws Exception {
        ReqUserDto userEmailNull = user.toBuilder().name(" ").build();
        checkValidationError(createUser(userEmailNull), NAME_BLANK_MESSAGE);
    }

    @Test
    void checkCreateUserWithExistedEmailValidation() throws Exception {
        createUser(user);
        createUser(user)
                .andExpect(status().isConflict());
    }
}
