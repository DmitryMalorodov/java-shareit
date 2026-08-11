package ru.practicum.shareit.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.Collection;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.users.UserData.user;
import static ru.practicum.shareit.users.UserData.user2;

@DisplayName("Проверка получения пользователей")
public class GetUsersTests extends UserTest {

    @Test
    void checkGettingOneUser() throws Exception {
        createUserResAct(user);

        Collection<RespUserDto> users = getUsers();
        for (RespUserDto u : users) {
            checkUser(u, user);
        }
    }

    @Test
    void checkGettingTwoUsers() throws Exception {
        createUserResAct(user);
        createUserResAct(user2);

        List<RespUserDto> users = getUsers();
        checkUser(users.getFirst(), user);
        checkUser(users.getLast(), user2);
    }

    @Test
    void checkGettingNoOneUser() throws Exception {
        mockMvc.perform(get(USERS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
