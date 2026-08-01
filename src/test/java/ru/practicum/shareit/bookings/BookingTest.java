package ru.practicum.shareit.bookings;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.SoftAssertions;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.GeneralAssertions.isEqualTo;
import static ru.practicum.shareit.GeneralAssertions.isNotNull;

public class BookingTest extends ShareItTests {
    static final String BOOKINGS = "/bookings";
    static final String BOOKINGS_OWNER = "/bookings/owner";
    static final String BOOKING_ID = "/bookings/{bookingId}";

    RespBookingDto getBookingDtoById(Long bookingId, Long userId) throws Exception {
        String jsonResponse = getBookingById(bookingId, userId)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, RespBookingDto.class);
    }

    ResultActions getBookingById(Long bookingId, Long userId) throws Exception {
        return mockMvc.perform(get(BOOKING_ID, bookingId)
                .header("X-Sharer-User-Id", userId));
    }

    Collection<RespBookingDto> getUserBookings(Long userId, String state) throws Exception {
        String jsonResponse = mockMvc.perform(get(BOOKINGS)
                .queryParam("state", state)
                .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, new TypeReference<>() {});
    }

    Collection<RespBookingDto> getUserItemsBookings(Long userId, String state) throws Exception {
        String jsonResponse = mockMvc.perform(get(BOOKINGS_OWNER)
                        .queryParam("state", state)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, new TypeReference<>() {});
    }

    void approveBooking(Long bookingId, String isApproved, Long userId) throws Exception {
        mockMvc.perform(patch(BOOKING_ID, bookingId)
                        .queryParam("approved", isApproved)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());
    }

    void checkBooking(RespBookingDto actBooking, ReqBookingDto expBooking, RespItemDto expItem,
                      RespUserDto expOwner, RespUserDto expBooker,
                      BookingStatus expStatus) {
        SoftAssertions softAssert = new SoftAssertions();

        isNotNull(actBooking.getId(),
                "ID бронироваия равен null", softAssert);
        isEqualTo(actBooking.getStart(), expBooking.getStart(),
                "Дата начала бронирования '%s' не совпадает с ожидаемой '%s'", softAssert);
        isEqualTo(actBooking.getEnd(), expBooking.getEnd(),
                "Дата окончания бронирования '%s' не совпадает с ожидаемой '%s'", softAssert);
        isEqualTo(actBooking.getStatus(), expStatus,
                "Статус бронирования '%s' не совпадает с ожидаемым '%s'", softAssert);

        checkItem(actBooking.getItem(), expItem, expOwner, softAssert);
        checkUser(actBooking.getBooker(), expBooker, softAssert);

        softAssert.assertAll();
    }
}
