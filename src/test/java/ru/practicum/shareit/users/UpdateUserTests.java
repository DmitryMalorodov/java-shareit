package ru.practicum.shareit.users;

import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void checkEmailNullValidation() throws Exception {
        newUser.setEmail(null);
        changeUserResAct(newUser, userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void checkEmailNotCorrectValidation() throws Exception {
        newUser.setEmail("email");
        checkValidationError(changeUserResAct(newUser, userId), EMAIL_NOT_CORRECT_MESSAGE);
    }

    @Test
    void checkNameNullValidation() throws Exception {
        newUser.setName(null);
        changeUserResAct(newUser, userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    void checkNameBlankValidation() throws Exception {
        newUser.setName(" ");
        changeUserResAct(newUser, userId)
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
