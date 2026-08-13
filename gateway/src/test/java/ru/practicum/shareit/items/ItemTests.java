package ru.practicum.shareit.items;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ReqItemDto;

import static ru.practicum.shareit.constant.ValidMessages.DESCRIPTION_BLANK_MESSAGE;
import static ru.practicum.shareit.constant.ValidMessages.NAME_BLANK_MESSAGE;
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
    }

    @Test
    void checkNameBlankValidation() throws Exception {
        ReqItemDto itemNameBlank = item.toBuilder().name(" ").build();
        checkValidationError(createItemResAct(itemNameBlank, USER_ID), NAME_BLANK_MESSAGE);
    }

    @Test
    void checkDescriptionNullValidation() throws Exception {
        ReqItemDto itemDescriptionNull = item.toBuilder().description(null).build();
        checkValidationError(createItemResAct(itemDescriptionNull, USER_ID), DESCRIPTION_BLANK_MESSAGE);
    }

    @Test
    void checkDescriptionBlankValidation() throws Exception {
        ReqItemDto itemDescriptionNBlank = item.toBuilder().description(" ").build();
        checkValidationError(createItemResAct(itemDescriptionNBlank, USER_ID), DESCRIPTION_BLANK_MESSAGE);
    }

    @Test
    void checkAvailableNullValidation() throws Exception {
        ReqItemDto itemAvailableNull = item.toBuilder().available(null).build();
        checkValidationError(createItemResAct(itemAvailableNull, USER_ID), AVAILABLE_NULL_MESSAGE);
    }

    @Test
    void checkCreateCommentWithoutText() throws Exception {
        checkValidationError(createCommentResAct(comment, 1L, 1L), COMMENT_BLANK_MESSAGE);
    }
}
