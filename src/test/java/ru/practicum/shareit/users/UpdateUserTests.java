package ru.practicum.shareit.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.UserDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.message.UserValidationMessages.EMAIL_NOT_CORRECT_MESSAGE;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка изменения пользователя")
public class UpdateUserTests extends UserTest {

    @Test
    void checkChangeUser() throws Exception {
        UserDto newUser = prepareReqBody(user);

        changeUser(newUser)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value(newUser.getEmail()))
                .andExpect(jsonPath("$.name").value(newUser.getName()));
    }

    @Test
    void checkEmailNullValidation() throws Exception {
        UserDto newUser = prepareReqBody(user);
        newUser.setEmail(null);

        changeUser(newUser)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void checkEmailNotCorrectValidation() throws Exception {
        UserDto newUser = prepareReqBody(user);
        newUser.setEmail("email");

        checkValidationError(changeUser(newUser), EMAIL_NOT_CORRECT_MESSAGE);
    }

    @Test
    void checkNameNullValidation() throws Exception {
        UserDto newUser = prepareReqBody(user);
        newUser.setName(null);

        changeUser(newUser)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    void checkNameBlankValidation() throws Exception {
        UserDto newUser = prepareReqBody(user);
        newUser.setName(" ");

        changeUser(newUser)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    private UserDto prepareReqBody(UserDto user) throws Exception {
        return user.toBuilder()
                .id(getIdFromObject(createUser(user)))
                .build();
    }
}
