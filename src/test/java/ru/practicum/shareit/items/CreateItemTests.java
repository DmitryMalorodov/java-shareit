package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ReqItemDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.message.ItemValidMessages.*;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка добавления вещи")
public class CreateItemTests extends ItemsTest {

    @Test
    void checkCreateItem() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        createItem(item, userId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(item.getName()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andExpect(jsonPath("$.available").value(item.getAvailable()))
                .andExpect(jsonPath("$.ownerId").value(userId));
    }

    @Test
    void checkNameNullValidation() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        ReqItemDto itemNameNull = item.toBuilder().name(null).build();
        checkValidationError(createItem(itemNameNull, userId), NAME_BLANK_MESSAGE);
    }

    @Test
    void checkNameBlankValidation() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        ReqItemDto itemNameBlank = item.toBuilder().name(" ").build();
        checkValidationError(createItem(itemNameBlank, userId), NAME_BLANK_MESSAGE);
    }

    @Test
    void checkDescriptionNullValidation() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        ReqItemDto itemDescriptionNull = item.toBuilder().description(null).build();
        checkValidationError(createItem(itemDescriptionNull, userId), DESCRIPTION_BLANK_MESSAGE);
    }

    @Test
    void checkDescriptionBlankValidation() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        ReqItemDto itemDescriptionNBlank = item.toBuilder().description(" ").build();
        checkValidationError(createItem(itemDescriptionNBlank, userId), DESCRIPTION_BLANK_MESSAGE);
    }

    @Test
    void checkAvailableNullValidation() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        ReqItemDto itemAvailableNull = item.toBuilder().available(null).build();
        checkValidationError(createItem(itemAvailableNull, userId), AVAILABLE_NULL_MESSAGE);
    }
}
