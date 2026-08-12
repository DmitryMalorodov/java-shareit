package ru.practicum.shareit.users;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.user.dto.ReqUserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

public class UserTest extends ShareItTests {
    static final String USERS_ID = "/users/{id}";

    ResultActions changeUserResAct(ReqUserDto user, Long userId) throws Exception {
        return mockMvc.perform(patch(USERS_ID, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
    }
}
