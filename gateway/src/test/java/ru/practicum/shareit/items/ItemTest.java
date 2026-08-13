package ru.practicum.shareit.items;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.item.dto.ReqCommentDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class ItemTest extends ShareItTests {
    static final String COMMENT = "/items/{itemId}/comment";

    ResultActions createCommentResAct(ReqCommentDto comment, Long userId, Long itemId) throws Exception {
        return mockMvc.perform(post(COMMENT, itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId)
                .content(objectMapper.writeValueAsString(comment)));
    }
}
