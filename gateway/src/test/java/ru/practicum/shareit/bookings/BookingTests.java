package ru.practicum.shareit.bookings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.practicum.shareit.booking.dto.ReqBookingDto;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static ru.practicum.shareit.bookings.BookingData.booking;
import static ru.practicum.shareit.bookings.BookingData.booking2;
import static ru.practicum.shareit.constant.ValidMessages.X_SHARER_USER_ID_ZERO_MESSAGE;
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
    void checkGetUserBookingsByBookingsOwner() throws Exception {
        checkValidationError(getUserBookingsResAct(USER_ID, "1"), String.format(INCORRECT_STATE_PARAMETER, "1"));
        verifyNoInteractions(bookingClient);
    }

    @Test
    void checkGetUserItemsBookingsByBookingsOwner() throws Exception {
        checkValidationError(getUserItemsBookingsResAct(USER_ID, "1"), String.format(INCORRECT_STATE_PARAMETER, "1"));
        verifyNoInteractions(bookingClient);
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
                        get("/bookings/1").header(HEADER_NAME, userId), "getBookingById"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        post("/bookings")
                            .header(HEADER_NAME, userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(booking2)), "create"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        patch("/bookings/1")
                                .header(HEADER_NAME, userId)
                                .param("approved", "true"), "approveBooking"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        get("/bookings").header(HEADER_NAME, userId), "getUserBookings"),

                Arguments.of((Function<Long, MockHttpServletRequestBuilder>) userId ->
                        get("/bookings/owner").header(HEADER_NAME, userId), "getUserItemsBookings")
        );
    }

    private ReqBookingDto prepareReqBody(LocalDateTime start, LocalDateTime end) {
        return booking.toBuilder()
                .itemId(ITEM_ID)
                .start(start)
                .end(end)
                .build();
    }
}
