package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.List;

import static ru.practicum.shareit.constant.message.ItemValidMessages.ITEM_NOT_FOUND_MESSAGE;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка получения вещи")
public class GetItemTests extends ItemsTest {

    @Test
    void checkGetItem() throws Exception {
        RespUserDto createdUser = createUserDto(user);
        RespItemDto createdItem = createItemDto(item, createdUser.getId());

        RespItemDto gettingItem = getItem(createdItem.getId(), createdUser.getId());
        checkItem(gettingItem, item, createdUser, List.of());
    }

    @Test
    void checkGetDoesNotExistItem() throws Exception {
        Long userId = getIdFromObject(createUser(user));
        Long itemNotExistId = 10L;
        checkNotFoundError(getItemResAct(itemNotExistId, userId), String.format(ITEM_NOT_FOUND_MESSAGE, itemNotExistId));
    }
}
