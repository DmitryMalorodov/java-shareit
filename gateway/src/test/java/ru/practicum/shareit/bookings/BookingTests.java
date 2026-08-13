package ru.practicum.shareit.bookings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.ReqBookingDto;

import static ru.practicum.shareit.bookings.BookingData.booking;
import static ru.practicum.shareit.constant.message.BookingValidMessages.*;

@DisplayName("Проверка валидации запросов /bookings")
public class BookingTests extends BookingTest {
    private static final Long USER_ID = 1L;
    private static final Long ITEM_ID = 1L;

    @Test
    void checkStartNullValidation() throws Exception {
        ReqBookingDto startNull = booking.toBuilder()
                .itemId(ITEM_ID)
                .start(null)
                .build();

        checkValidationError(createBookingResAct(startNull, USER_ID), START_DATE_NULL_MESSAGE);
    }

    @Test
    void checkEndNullValidation() throws Exception {
        ReqBookingDto endNull = booking.toBuilder()
                .itemId(ITEM_ID)
                .end(null)
                .build();

        checkValidationError(createBookingResAct(endNull, USER_ID), END_DATE_NULL_MESSAGE);
    }

    @Test
    void checkItemIdNullValidation() throws Exception {
        checkValidationError(createBookingResAct(booking, USER_ID), ITEM_ID_NULL_MESSAGE);
    }

    @Test
    void checkGetUserItemsBookingsByBookingsOwner() throws Exception {
        checkValidationError(getUserItemsBookingsResAct(USER_ID, "1"), "Unknown state: 1");
    }
}
