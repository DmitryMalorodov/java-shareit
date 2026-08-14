package ru.practicum.shareit.bookings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.ReqBookingDto;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verifyNoInteractions;
import static ru.practicum.shareit.bookings.BookingData.booking;
import static ru.practicum.shareit.constant.message.BookingValidMessages.*;

@DisplayName("Проверка валидации запросов /bookings")
public class BookingTests extends BookingTest {
    private static final Long USER_ID = 1L;
    private static final Long ITEM_ID = 1L;
    private static final LocalDateTime TODAY = LocalDateTime.now();
    private static final LocalDateTime YESTERDAY = LocalDateTime.now().minusDays(1);
    private static final LocalDateTime TOMORROW = LocalDateTime.now().plusDays(1);

    @Test
    void checkStartNullValidation() throws Exception {
        ReqBookingDto startNull = prepareReqBody(null, TOMORROW);
        checkValidationError(createBookingResAct(startNull, USER_ID), START_DATE_NULL_MESSAGE);
        verifyNoInteractions(bookingClient);
    }

    @Test
    void checkStartDateValidation() throws Exception {
        ReqBookingDto startYesterday = prepareReqBody(YESTERDAY, TOMORROW);
        checkValidationError(createBookingResAct(startYesterday, USER_ID), BOOKING_START_DATE_VALID_MESSAGE);
        verifyNoInteractions(bookingClient);
    }

    @Test
    void checkEndNullValidation() throws Exception {
        ReqBookingDto endNull = prepareReqBody(TOMORROW, null);
        checkValidationError(createBookingResAct(endNull, USER_ID), END_DATE_NULL_MESSAGE);
        verifyNoInteractions(bookingClient);
    }

    @Test
    void checkEndDateValidation() throws Exception {
        ReqBookingDto endToday = prepareReqBody(TOMORROW, TODAY);
        checkValidationError(createBookingResAct(endToday, USER_ID), BOOKING_END_DATE_VALID_MESSAGE);
        verifyNoInteractions(bookingClient);
    }

    @Test
    void checkItemIdNullValidation() throws Exception {
        checkValidationError(createBookingResAct(booking, USER_ID), ITEM_ID_NULL_MESSAGE);
        verifyNoInteractions(bookingClient);
    }

    @Test
    void checkGetUserItemsBookingsByBookingsOwner() throws Exception {
        checkValidationError(getUserItemsBookingsResAct(USER_ID, "1"), String.format(INCORRECT_STATE_PARAMETER, "1"));
        verifyNoInteractions(bookingClient);
    }

    private ReqBookingDto prepareReqBody(LocalDateTime start, LocalDateTime end) {
        return booking.toBuilder()
                .itemId(ITEM_ID)
                .start(start)
                .end(end)
                .build();
    }
}
