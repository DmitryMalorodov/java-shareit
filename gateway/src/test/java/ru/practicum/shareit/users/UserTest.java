package ru.practicum.shareit.users;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.user.UserClient;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.ReqUserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@WebMvcTest(UserController.class)
public class UserTest extends ShareItTests {
    static final String USERS_ID = "/users/{id}";

    @MockBean
    protected UserClient userClient;

    ResultActions changeUserResAct(ReqUserDto user, Long userId) throws Exception {
        return mockMvc.perform(patch(USERS_ID, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
    }
}
