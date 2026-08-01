package ru.practicum.shareit.users;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.user.dto.ReqUserDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTest extends ShareItTests {
    static final String USERS = "/users";
    static final String USERS_ID = "/users/{id}";

    ResultActions changeUser(ReqUserDto user, Long userId) throws Exception {
        return mockMvc.perform(patch(USERS_ID, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
    }

    RespUserDto changeUserDto(ReqUserDto user, Long userId) throws Exception {
        String jsonResponse = changeUser(user, userId)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, RespUserDto.class);
    }
}
