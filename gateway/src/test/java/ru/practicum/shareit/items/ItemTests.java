package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.practicum.shareit.item.dto.ReqItemDto;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static ru.practicum.shareit.constant.ValidMessages.*;
import static ru.practicum.shareit.constant.message.ItemValidMessages.AVAILABLE_NULL_MESSAGE;
import static ru.practicum.shareit.constant.message.ItemValidMessages.COMMENT_BLANK_MESSAGE;
import static ru.practicum.shareit.items.ItemData.*;

@DisplayName("Проверка валидации запросов /items")
public class ItemTests extends ItemTest {
    private static final Long USER_ID = 1L;

    @Test
    void checkNameNullValidation() throws Exception {
        ReqItemDto itemNameNull = item.toBuilder().name(null).build();
        checkValidationError(createItemResAct(itemNameNull, USER_ID), NAME_BLANK_MESSAGE);
        verifyNoInteractions(itemClient);
    }

    @Test
    void checkNameBlankValidation() throws Exception {
        ReqItemDto itemNameBlank = item.toBuilder().name(" ").build();
        checkValidationError(createItemResAct(itemNameBlank, USER_ID), NAME_BLANK_MESSAGE);
        verifyNoInteractions(itemClient);
    }

    @Test
    void checkDescriptionNullValidation() throws Exception {
        ReqItemDto itemDescriptionNull = item.toBuilder().description(null).build();
        checkValidationError(createItemResAct(itemDescriptionNull, USER_ID), DESCRIPTION_BLANK_MESSAGE);
        verifyNoInteractions(itemClient);
    }

    @Test
    void checkDescriptionBlankValidation() throws Exception {
        ReqItemDto itemDescriptionNBlank = item.toBuilder().description(" ").build();
        checkValidationError(createItemResAct(itemDescriptionNBlank, USER_ID), DESCRIPTION_BLANK_MESSAGE);
        verifyNoInteractions(itemClient);
    }

    @Test
    void checkAvailableNullValidation() throws Exception {
        ReqItemDto itemAvailableNull = item.toBuilder().available(null).build();
        checkValidationError(createItemResAct(itemAvailableNull, USER_ID), AVAILABLE_NULL_MESSAGE);
        verifyNoInteractions(itemClient);
    }

    @Test
    void checkCreateCommentWithoutText() throws Exception {
        checkValidationError(createCommentResAct(comment, 1L, 1L), COMMENT_BLANK_MESSAGE);
        verifyNoInteractions(itemClient);
    }

    @ParameterizedTest(name = "Проверка валидации X-Sharer-User-Id для метода {1}")
    @MethodSource("getMethods")
    void checkValidationXUserId(Function<Long, MockHttpServletRequestBuilder> function, String methodName) throws Exception {
        checkValidationError(mockMvc.perform(function.apply(0L)), X_SHARER_USER_ID_ZERO_MESSAGE);
        checkValidationError(mockMvc.perform(function.apply(-1L)), X_SHARER_USER_ID_ZERO_MESSAGE);
    }

    private static Stream<Arguments> getMethods() {
        return Stream.of(
                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        get("/items/1").header(HEADER_NAME, userId), "getItemById"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        get("/items").header(HEADER_NAME, userId), "getUserItems"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        post("/items")
                                .header(HEADER_NAME, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(item)), "createItem"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        patch("/items/1")
                                .header(HEADER_NAME, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(item)), "update"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        get("/items/search")
                                .header(HEADER_NAME, userId)
                                .param("text", "дрель"), "search"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        post("/items/1/comment")
                                .header(HEADER_NAME, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(comment2)), "createComment")
        );
    }
}
