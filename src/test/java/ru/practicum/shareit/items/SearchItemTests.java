package ru.practicum.shareit.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.GeneralAssertions;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.List;

import static ru.practicum.shareit.items.ItemData.*;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка поиска вещи")
public class SearchItemTests extends ItemsTest {
    private RespUserDto createdUser;

    @BeforeEach
    void setUp() throws Exception {
        createdUser = createUser(user);
        createItemResAct(item, createdUser.getId());
        createItemResAct(item2, createdUser.getId());
        createItemResAct(item3, createdUser.getId());
    }

    @Test
    void checkSearchItemOnlyAvailable() throws Exception {
        List<RespItemDto> foundItems = search(createdUser.getId(), "des");
        GeneralAssertions.isTrue(foundItems.size() == 1,
                "Размер списка вещей не соответствует ожидаемому");
        checkItem(foundItems.getFirst(), item, createdUser, List.of());
    }

    @Test
    void checkSearchItemByName() throws Exception {
        List<RespItemDto> foundItems = search(createdUser.getId(), "NAm");
        GeneralAssertions.isTrue(foundItems.size() == 1,
                "Размер списка вещей не соответствует ожидаемому");
        checkItem(foundItems.getFirst(), item3, createdUser, List.of());
    }

    @Test
    void checkSearchItemByNameAndDescription() throws Exception {
        List<RespItemDto> foundItems = search(createdUser.getId(), "111");
        GeneralAssertions.isTrue(foundItems.size() == 2,
                "Размер списка вещей не соответствует ожидаемому");
        checkItem(foundItems.getFirst(), item, createdUser, List.of());
        checkItem(foundItems.getLast(), item3, createdUser, List.of());
    }

    @Test
    void checkSearchItemByBlank() throws Exception {
        List<RespItemDto> foundItems = search(createdUser.getId(), " ");
        GeneralAssertions.isTrue(foundItems.isEmpty(),
                "Размер списка вещей не соответствует ожидаемому");
    }
}
