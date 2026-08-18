package ru.practicum.shareit.items;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.item.ItemClient;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.ReqCommentDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ItemController.class)
public class ItemTest extends ShareItTests {
    static final String COMMENT = "/items/{itemId}/comment";

    @MockBean
    protected ItemClient itemClient;

    ResultActions createCommentResAct(ReqCommentDto comment, Long userId, Long itemId) throws Exception {
        return mockMvc.perform(post(COMMENT, itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId)
                .content(objectMapper.writeValueAsString(comment)));
    }
}
