package ru.practicum.shareit.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.practicum.shareit.request.dto.ReqItemRequestDto;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static ru.practicum.shareit.constant.ValidMessages.DESCRIPTION_BLANK_MESSAGE;
import static ru.practicum.shareit.constant.ValidMessages.X_SHARER_USER_ID_ZERO_MESSAGE;
import static ru.practicum.shareit.request.RequestData.request;

@DisplayName("Проверка валидации запросов /requests")
public class RequestTests extends RequestTest {
    private static final Long USER_ID = 1L;

    @Test
    void checkDescriptionNullValidation() throws Exception {
        ReqItemRequestDto nullDescription = request.toBuilder().description(null).build();
        checkValidationError(createRequestResAct(nullDescription, USER_ID), DESCRIPTION_BLANK_MESSAGE);
        verifyNoInteractions(requestClient);
    }

    @Test
    void checkDescriptionBlankValidation() throws Exception {
        ReqItemRequestDto nullDescription = request.toBuilder().description(" ").build();
        checkValidationError(createRequestResAct(nullDescription, USER_ID), DESCRIPTION_BLANK_MESSAGE);
        verifyNoInteractions(requestClient);
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
                        post("/requests")
                                .header(HEADER_NAME, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request)), "create"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        get("/requests/all").header(HEADER_NAME, userId), "getItemRequests"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        get("/requests").header(HEADER_NAME, userId), "getUserItemRequests"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        get("/requests/1").header(HEADER_NAME, userId), "getItemRequestById")
        );
    }
}
