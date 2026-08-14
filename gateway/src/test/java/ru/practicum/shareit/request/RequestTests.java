package ru.practicum.shareit.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.ReqItemRequestDto;

import static org.mockito.Mockito.verifyNoInteractions;
import static ru.practicum.shareit.constant.ValidMessages.DESCRIPTION_BLANK_MESSAGE;
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
}
