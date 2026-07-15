package ru.practicum.shareit.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.UserDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.message.UserValidationMessages.*;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка добавления пользователя")
public class CreateUserTests extends UserTest {

    @Test
    void checkCreateUser() throws Exception {
        createUser(user)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    void checkEmailNullValidation() throws Exception {
        UserDto userEmailNull = user.toBuilder().email(null).build();
        checkValidationError(createUser(userEmailNull), EMAIL_BLANK_MESSAGE);
    }

    @Test
    void checkEmailNotCorrectValidation() throws Exception {
        UserDto userEmailNotCorrect = user.toBuilder().email("email").build();
        checkValidationError(createUser(userEmailNotCorrect), EMAIL_NOT_CORRECT_MESSAGE);
    }

    @Test
    void checkNameNullValidation() throws Exception {
        UserDto userEmailNull = user.toBuilder().name(null).build();
        checkValidationError(createUser(userEmailNull), NAME_BLANK_MESSAGE);
    }

    @Test
    void checkNameBlankValidation() throws Exception {
        UserDto userEmailNull = user.toBuilder().name(" ").build();
        checkValidationError(createUser(userEmailNull), NAME_BLANK_MESSAGE);
    }

    @Test
    void checkCreateUserWithExistedEmailValidation() throws Exception {
        createUser(user);
        createUser(user)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error")
                        .value(String.format(USER_ALREADY_EXISTS_WITH_EMAIL, user.getEmail())));
    }
}
