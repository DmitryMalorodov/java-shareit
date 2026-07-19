package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.message.ItemValidMessages.ITEM_NOT_FOUND_MESSAGE;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка получения вещи")
public class GetItemTests extends ItemsTest {

    @Test
    void checkGetItem() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        createItem(item, userId);

        mockMvc.perform(get(ITEMS_ID, userId).header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(item.getName()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andExpect(jsonPath("$.available").value(item.getAvailable()))
                .andExpect(jsonPath("$.ownerId").value(userId));
    }

    @Test
    void checkGetDoesNotExistItem() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        Long itemNotExistId = 10L;

        mockMvc.perform(get(ITEMS_ID, itemNotExistId).header("X-Sharer-User-Id", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(String.format(ITEM_NOT_FOUND_MESSAGE, itemNotExistId)));
    }
}
