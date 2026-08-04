package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ReqItemDto;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.List;

import static ru.practicum.shareit.constant.message.ItemValidMessages.ITEM_UPDATE_ACCESS_MESSAGE;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка редактирования вещи")
public class UpdateItemTests extends ItemsTest {

    @Test
    void checkChangeItem() throws Exception {
        RespUserDto createdUser = createUser(user);
        Long itemId = getIdFromObject(createItemResAct(item, createdUser.getId()));
        ReqItemDto newItem = prepareReqBody(item);

        RespItemDto changedItem = changeItem(newItem, createdUser.getId(), itemId);
        checkItem(changedItem, newItem, createdUser, List.of());
    }

    @Test
    void checkChangeItemWithOtherUser() throws Exception {
        Long userId = getIdFromObject(createUserResAct(user));
        Long randomUserId = 100L;

        Long itemId = getIdFromObject(createItemResAct(item, userId));
        ReqItemDto newItem = prepareReqBody(item);

        checkForbiddenError(changeItemResAct(newItem, randomUserId, itemId), ITEM_UPDATE_ACCESS_MESSAGE);
    }

    private ReqItemDto prepareReqBody(ReqItemDto item) {
        return item.toBuilder()
                .name("otherName")
                .description("otherDesc")
                .available(false)
                .build();
    }
}
