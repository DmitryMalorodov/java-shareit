package ru.practicum.shareit.bookings;

import org.assertj.core.api.SoftAssertions;
import org.springframework.test.web.servlet.ResultActions;
import ru.practicum.shareit.ShareItTests;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.RespUserDto;
import ru.practicum.shareit.user.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static ru.practicum.shareit.GeneralAssertions.isEqualTo;
import static ru.practicum.shareit.GeneralAssertions.isNotNull;
import static ru.practicum.shareit.bookings.BookingData.booking;

public class BookingTest extends ShareItTests {
    static final String BOOKING_ID = "/bookings/{bookingId}";

    RespBookingDto getBookingDtoById(Long bookingId, Long userId) throws Exception {
        String jsonResponse = getBookingByIdResAct(bookingId, userId)
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        return objectMapper.readValue(jsonResponse, RespBookingDto.class);
    }

    ResultActions getBookingById(Long bookingId, Long userId) throws Exception {
        return getBookingByIdResAct(bookingId, userId);
    }

    private ResultActions getBookingByIdResAct(Long bookingId, Long userId) throws Exception {
        return mockMvc.perform(get(BOOKING_ID, bookingId, userId)
                .header("X-Sharer-User-Id", userId)
                .content(objectMapper.writeValueAsString(booking)));
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

        checkItem(actBooking.getItem(), expItem, softAssert);
        checkUser(actBooking.getItem().getOwner(), expOwner, softAssert);
        checkUser(actBooking.getBooker(), expBooker, softAssert);
    }

    void checkItem(Item actItem, RespItemDto expItem, SoftAssertions softAssert) {
        isEqualTo(actItem.getId(), expItem.getId(),
                "ID вещи '%d' не совпадает с ожидаемым '%d'", softAssert);
        isEqualTo(actItem.getName(), expItem.getName(),
                "Имя вещи '%s' не совпадает с ожидаемым '%s'", softAssert);
        isEqualTo(actItem.getDescription(), expItem.getDescription(),
                "Описание вещи '%s' не совпадает с ожидаемым '%s'", softAssert);
        isEqualTo(actItem.getAvailable(), expItem.getAvailable(),
                "Доступность вещи '%b' не совпадает с ожидаемой '%b'", softAssert);
    }

    void checkUser(User actUser, RespUserDto expUser, SoftAssertions softAssert) {
        isEqualTo(actUser.getId(), expUser.getId(),
                "ID пользователя '%d' не совпадает с ожидаемым '%d'", softAssert);
        isEqualTo(actUser.getName(), expUser.getName(),
                "Имя пользователя '%s' не совпадает с ожидаемым '%s'", softAssert);
        isEqualTo(actUser.getEmail(), expUser.getEmail(),
                "Email пользователя '%s' не совпадает с ожидаемым '%s'", softAssert);
    }
}
