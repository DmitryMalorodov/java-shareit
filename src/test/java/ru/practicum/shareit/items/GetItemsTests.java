package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.GeneralAssertions;
import ru.practicum.shareit.item.dto.GetUserItemsDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.List;

import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка получения вещей")
public class GetItemsTests extends ItemsTest {

    @Test
    void checkGettingOneItem() throws Exception {
        RespUserDto createdUser = createUser(user);
        createItemResAct(item, createdUser.getId());

        List<GetUserItemsDto> items = getItems(createdUser.getId());
        GeneralAssertions.isTrue(items.size() == 1,
                "Размер списка вещей не соответствует ожидаемому");
        checkItem(items.getFirst(), item, createdUser, List.of());
    }

    @Test
    void checkGettingTwoItems() throws Exception {
        RespUserDto createdUser = createUser(user);
        createItemResAct(item, createdUser.getId());
        createItemResAct(item, createdUser.getId());

        List<GetUserItemsDto> items = getItems(createdUser.getId());
        GeneralAssertions.isTrue(items.size() == 2,
                "Размер списка вещей не соответствует ожидаемому");
        checkItem(items.getFirst(), item, createdUser, List.of());
        checkItem(items.getLast(), item, createdUser, List.of());
    }

    @Test
    void checkGettingNoOneItem() throws Exception {
        Long userId = getIdFromObject(createUserResAct(user));

        List<GetUserItemsDto> items = getItems(userId);
        GeneralAssertions.isTrue(items.isEmpty(),
                "Размер списка вещей не соответствует ожидаемому");
    }
}
