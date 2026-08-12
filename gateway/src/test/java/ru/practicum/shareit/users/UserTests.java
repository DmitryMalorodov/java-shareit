package ru.practicum.shareit.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.ReqUserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.ValidMessages.NAME_BLANK_MESSAGE;
import static ru.practicum.shareit.constant.message.UserValidationMessages.*;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка валидации запросов /users")
public class UserTests extends UserTest {
    private static final Long USER_ID = 1L;

    @Test
    void checkEmailNullValidation() throws Exception {
        ReqUserDto userEmailNull = user.toBuilder().email(null).build();
        checkValidationError(createUserResAct(userEmailNull), EMAIL_BLANK_MESSAGE);
    }

    @Test
    void checkEmailNotCorrectValidation() throws Exception {
        ReqUserDto userEmailNotCorrect = user.toBuilder().email("email").build();
        checkValidationError(createUserResAct(userEmailNotCorrect), EMAIL_NOT_CORRECT_MESSAGE);
    }

    @Test
    void checkNameNullValidation() throws Exception {
        ReqUserDto userEmailNull = user.toBuilder().name(null).build();
        checkValidationError(createUserResAct(userEmailNull), NAME_BLANK_MESSAGE);
    }

    @Test
    void checkNameBlankValidation() throws Exception {
        ReqUserDto userEmailNull = user.toBuilder().name(" ").build();
        checkValidationError(createUserResAct(userEmailNull), NAME_BLANK_MESSAGE);
    }

    @Test
    void checkCreateUserWithExistedEmailValidation() throws Exception {
        createUserResAct(user);
        createUserResAct(user)
                .andExpect(status().isConflict());
    }

    @Test
    void checkGetDoesNotExistUser() throws Exception {
        Long userNotExistId = 10L;

        mockMvc.perform(get(USERS_ID, userNotExistId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(String.format(USER_NOT_FOUND_MESSAGE, userNotExistId)));
    }

    @Test
    void checkUpdateEmailNullValidation() throws Exception {
        changeUserResAct(user, USER_ID)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void checkUpdateEmailNotCorrectValidation() throws Exception {
        ReqUserDto userWithNotCorrectEmail = user.toBuilder().email("123").build();
        checkValidationError(changeUserResAct(userWithNotCorrectEmail, USER_ID), EMAIL_NOT_CORRECT_MESSAGE);
    }

    @Test
    void checkUpdateNameNullValidation() throws Exception {
        changeUserResAct(user, USER_ID)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    void checkUpdateNameBlankValidation() throws Exception {
        changeUserResAct(user, USER_ID)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    void checkDeleteUserWithDoesNotExistId() throws Exception {
        Long notExistFilmId = 10L;
        mockMvc.perform(delete(USERS_ID, notExistFilmId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(String.format(USER_NOT_FOUND_MESSAGE, notExistFilmId)));
    }
}
