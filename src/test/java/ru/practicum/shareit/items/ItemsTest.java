package ru.practicum.shareit.items;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.SoftAssertions;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.GeneralAssertions.*;

public class ItemsTest extends ShareItTests {
    static final String ITEMS = "/items";
    static final String ITEMS_ID = "/items/{itemId}";
    static final String ITEMS_SEARCH = "/items/search";
    static final String COMMENT = "/items/{itemId}/comment";

    ResultActions changeItem(ReqItemDto item, Long userId, Long itemId) throws Exception {
        return mockMvc.perform(patch(ITEMS_ID, itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId)
                .content(objectMapper.writeValueAsString(item)));
    }

    RespItemDto changeItemDto(ReqItemDto item, Long userId, Long itemId) throws Exception {
        String jsonResponse = changeItem(item, userId, itemId)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, RespItemDto.class);
    }

    ResultActions createComment(ReqCommentDto comment, Long userId, Long itemId) throws Exception {
        return mockMvc.perform(post(COMMENT, itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId)
                .content(objectMapper.writeValueAsString(comment)));
    }

    RespCommentDto createCommentDto(ReqCommentDto comment, Long userId, Long itemId) throws Exception {
        String jsonResponse = createComment(comment, userId, itemId)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, RespCommentDto.class);
    }

    List<GetUserItemsDto> getItems(Long userId) throws Exception {
        String jsonResponse = mockMvc.perform(get(ITEMS)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, new TypeReference<>() {});
    }

    RespItemDto getItem(Long itemId, Long userId) throws Exception {
        String jsonResponse = getItemResAct(itemId, userId)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, RespItemDto.class);
    }

    ResultActions getItemResAct(Long itemId, Long userId) throws Exception {
        return mockMvc.perform(get(ITEMS_ID, itemId)
                .header("X-Sharer-User-Id", userId));
    }

    List<RespItemDto> search(Long userId, String searchText) throws Exception {
        String jsonResponse = mockMvc.perform(get(ITEMS_SEARCH)
                .queryParam("text", searchText)
                .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, new TypeReference<>() {});
    }

    void checkComment(RespCommentDto actComment, ReqCommentDto expComment, RespItemDto expItem, RespUserDto expUser) {
        SoftAssertions softAssert = new SoftAssertions();

        isNotNull(actComment.getId(), "ID комментария '%d' отсутствует", softAssert);
        isEqualTo(actComment.getText(), expComment.getText(),
                "Текст комментария '%s' не соответствует ожидаемому '%s'", softAssert);
        isCloseTo(actComment.getCreated(), LocalDateTime.now(),
                "Дата/Время создания комментария '%s' не соответствует ожидаемому '%s'", softAssert);
        checkItem(actComment.getItem(), expItem, expUser, softAssert);
        checkUser(actComment.getAuthor(), expUser, softAssert);

        softAssert.assertAll();
    }
}
