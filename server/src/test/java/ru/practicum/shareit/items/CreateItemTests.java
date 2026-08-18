package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.List;

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
}
