package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ReqItemDto;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.List;

import static ru.practicum.shareit.constant.message.ItemValidMessages.*;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка добавления вещи")
public class CreateItemTests extends ItemsTest {

    @Test
    void checkCreateItem() throws Exception {
        RespUserDto createdUser = createUser(user);
        RespItemDto createdItem = createItem(item, createdUser.getId());
        checkItem(createdItem, item, createdUser, List.of());
    }

    @Test
    void checkNameNullValidation() throws Exception {
        Long userId = getIdFromObject(createUserResAct(user));
        ReqItemDto itemNameNull = item.toBuilder().name(null).build();
        checkValidationError(createItemResAct(itemNameNull, userId), NAME_BLANK_MESSAGE);
    }

    @Test
    void checkNameBlankValidation() throws Exception {
        Long userId = getIdFromObject(createUserResAct(user));
        ReqItemDto itemNameBlank = item.toBuilder().name(" ").build();
        checkValidationError(createItemResAct(itemNameBlank, userId), NAME_BLANK_MESSAGE);
    }

    @Test
    void checkDescriptionNullValidation() throws Exception {
        Long userId = getIdFromObject(createUserResAct(user));
        ReqItemDto itemDescriptionNull = item.toBuilder().description(null).build();
        checkValidationError(createItemResAct(itemDescriptionNull, userId), DESCRIPTION_BLANK_MESSAGE);
    }

    @Test
    void checkDescriptionBlankValidation() throws Exception {
        Long userId = getIdFromObject(createUserResAct(user));
        ReqItemDto itemDescriptionNBlank = item.toBuilder().description(" ").build();
        checkValidationError(createItemResAct(itemDescriptionNBlank, userId), DESCRIPTION_BLANK_MESSAGE);
    }

    @Test
    void checkAvailableNullValidation() throws Exception {
        Long userId = getIdFromObject(createUserResAct(user));
        ReqItemDto itemAvailableNull = item.toBuilder().available(null).build();
        checkValidationError(createItemResAct(itemAvailableNull, userId), AVAILABLE_NULL_MESSAGE);
    }
}
