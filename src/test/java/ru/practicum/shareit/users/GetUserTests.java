package ru.practicum.shareit.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.message.UserValidationMessages.USER_NOT_FOUND_MESSAGE;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка получения пользователя")
public class GetUserTests extends UserTest {

    @Test
    void checkGetUser() throws Exception {
        Long userId = getIdFromObject(createUser(user));

        mockMvc.perform(get(USERS_ID, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    void checkGetDoesNotExistUser() throws Exception {
        Long userNotExistId = 10L;

        mockMvc.perform(get(USERS_ID, userNotExistId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(String.format(USER_NOT_FOUND_MESSAGE, userNotExistId)));
    }
}
