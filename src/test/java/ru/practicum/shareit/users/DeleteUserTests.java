package ru.practicum.shareit.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.message.UserValidationMessages.USER_NOT_FOUND_MESSAGE;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка удаления пользователя")
public class DeleteUserTests extends UserTest {

    @Test
    void checkDeleteUser() throws Exception {
        Long userId = getIdFromObject(createUserResAct(user));

        mockMvc.perform(delete(USERS_ID, userId))
                .andExpect(status().isOk());

        mockMvc.perform(get(USERS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void checkDeleteUserWithDoesNotExistId() throws Exception {
        Long notExistFilmId = 10L;
        mockMvc.perform(delete(USERS_ID, notExistFilmId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(String.format(USER_NOT_FOUND_MESSAGE, notExistFilmId)));
    }
}
