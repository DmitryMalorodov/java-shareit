package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ReqItemDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.message.ItemValidMessages.ITEM_UPDATE_ACCESS_MESSAGE;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка редактирования вещи")
public class UpdateItemTests extends ItemsTest {

    @Test
    void checkChangeItem() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        Long itemId = getIdFromObject(createItem(item, userId));
        ReqItemDto newItem = prepareReqBody(item);

        changeItem(newItem, userId, itemId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(newItem.getName()))
                .andExpect(jsonPath("$.description").value(newItem.getDescription()))
                .andExpect(jsonPath("$.available").value(newItem.getAvailable()))
                .andExpect(jsonPath("$.ownerId").value(userId));
    }

    @Test
    void checkChangeItemWithOtherUser() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        Long randomUserId = 100L;

        Long itemId = getIdFromObject(createItem(item, userId));
        ReqItemDto newItem = prepareReqBody(item);

        changeItem(newItem, randomUserId, itemId)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(ITEM_UPDATE_ACCESS_MESSAGE));
    }

    private ReqItemDto prepareReqBody(ReqItemDto item) {
        return item.toBuilder()
                .name("otherName")
                .description("otherDesc")
                .available(false)
                .build();
    }
}
