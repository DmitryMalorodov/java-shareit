package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка получения вещей")
public class GetItemsTests extends ItemsTest {

    @Test
    void checkGettingOneItem() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        createItem(item, userId);

        mockMvc.perform(get(ITEMS, userId).header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value(item.getName()))
                .andExpect(jsonPath("$[0].description").value(item.getDescription()))
                .andExpect(jsonPath("$[0].available").value(item.getAvailable()))
                .andExpect(jsonPath("$[0].ownerId").value(userId));
    }

    @Test
    void checkGettingTwoItems() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        createItem(item, userId);
        createItem(item, userId);

        mockMvc.perform(get(ITEMS, userId).header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").value(item.getName()))
                .andExpect(jsonPath("$[0].description").value(item.getDescription()))
                .andExpect(jsonPath("$[0].available").value(item.getAvailable()))
                .andExpect(jsonPath("$[0].ownerId").value(userId))
                .andExpect(jsonPath("$[1].id").exists())
                .andExpect(jsonPath("$[1].name").value(item.getName()))
                .andExpect(jsonPath("$[1].description").value(item.getDescription()))
                .andExpect(jsonPath("$[1].available").value(item.getAvailable()))
                .andExpect(jsonPath("$[1].ownerId").value(userId));
    }

    @Test
    void checkGettingNoOneItem() throws Exception {
        Long userId = getIdFromObject(createUser(user));

        mockMvc.perform(get(ITEMS, userId).header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
