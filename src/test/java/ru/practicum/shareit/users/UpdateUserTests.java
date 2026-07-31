package ru.practicum.shareit.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.ReqUserDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.message.UserValidationMessages.EMAIL_NOT_CORRECT_MESSAGE;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка изменения пользователя")
public class UpdateUserTests extends UserTest {

    @Test
    void checkChangeUser() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        ReqUserDto newUser = prepareReqBody(user);

        RespUserDto changedUser = changeUserDto(newUser, userId);
        checkUser(changedUser, newUser);
    }

    @Test
    void checkEmailNullValidation() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        ReqUserDto newUser = prepareReqBody(user);
        newUser.setEmail(null);

        changeUser(newUser, userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void checkEmailNotCorrectValidation() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        ReqUserDto newUser = prepareReqBody(user);
        newUser.setEmail("email");

        checkValidationError(changeUser(newUser, userId), EMAIL_NOT_CORRECT_MESSAGE);
    }

    @Test
    void checkNameNullValidation() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        ReqUserDto newUser = prepareReqBody(user);
        newUser.setName(null);

        changeUser(newUser, userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    void checkNameBlankValidation() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        ReqUserDto newUser = prepareReqBody(user);
        newUser.setName(" ");

        changeUser(newUser, userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    private ReqUserDto prepareReqBody(ReqUserDto user) {
        return user.toBuilder()
                .name("otherName")
                .email("other@email.ru")
                .build();
    }
}
