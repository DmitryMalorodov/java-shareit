package ru.practicum.shareit.items;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.item.dto.ReqItemDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

public class ItemsTest extends ShareItTests {
    static final String ITEMS = "/items";
    static final String ITEMS_ID = "/items/{itemId}";
    static final String ITEMS_SEARCH = "/items/search";

    ResultActions changeItem(ReqItemDto item, Long userId, Long itemId) throws Exception {
        return mockMvc.perform(patch(ITEMS_ID, itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId)
                .content(objectMapper.writeValueAsString(item)));
    }
}
