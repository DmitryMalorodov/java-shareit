package ru.practicum.shareit.bookings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import static ru.practicum.shareit.bookings.BookingData.booking;
import static ru.practicum.shareit.constant.message.BookingValidMessages.BOOKING_ACCESS_ERROR;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.*;

@DisplayName("Проверка получения бронирования вещи")
public class GetBookingTests extends BookingTest {

    @Test
    void checkGetBookingByUserThatDidBooking() throws Exception {
        RespUserDto createdUser = createUserDto(user);
        RespUserDto createdUser2 = createUserDto(user2);
        RespItemDto createdItem = createItemDto(item, createdUser2.getId());

        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        RespBookingDto createdBooking = createBookingDto(reqBody, createdUser.getId());

        RespBookingDto receivedBooking = getBookingDtoById(createdBooking.getId(), createdUser.getId());
        checkBooking(receivedBooking, reqBody, createdItem, createdUser2, createdUser, BookingStatus.WAITING);
    }

    @Test
    void checkGetBookingByUserThatOwnerOfItem() throws Exception {
        RespUserDto createdUser = createUserDto(user);
        RespUserDto createdUser2 = createUserDto(user2);
        RespItemDto createdItem = createItemDto(item, createdUser.getId());

        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        RespBookingDto createdBooking = createBookingDto(reqBody, createdUser2.getId());

        RespBookingDto receivedBooking = getBookingDtoById(createdBooking.getId(), createdUser.getId());
        checkBooking(receivedBooking, reqBody, createdItem, createdUser, createdUser2, BookingStatus.WAITING);
    }

    @Test
    void checkGetBookingByOtherUser() throws Exception {
        RespUserDto createdUser = createUserDto(user);
        RespUserDto createdUser2 = createUserDto(user2);
        RespUserDto createdUser3 = createUserDto(user3);
        RespItemDto createdItem = createItemDto(item, createdUser.getId());

        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        RespBookingDto createdBooking = createBookingDto(reqBody, createdUser2.getId());

        checkNotFoundError(getBookingById(createdBooking.getId(), createdUser3.getId()), BOOKING_ACCESS_ERROR);
    }
}
