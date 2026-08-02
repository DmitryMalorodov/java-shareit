package ru.practicum.shareit.bookings;

import org.junit.jupiter.api.BeforeEach;
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
    private RespUserDto createdUser;
    private RespUserDto createdUser2;

    @BeforeEach
    void setUp() throws Exception {
        createdUser = createUser(user);
        createdUser2 = createUser(user2);
    }

    @Test
    void checkGetBookingByUserThatDidBooking() throws Exception {
        //создание вещи
        RespItemDto createdItem = createItem(item, createdUser2.getId());

        //создание брони на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        RespBookingDto createdBooking = createBooking(reqBody, createdUser.getId());

        //получение созданной брони ее создателем и ее проверка
        RespBookingDto receivedBooking = getBookingById(createdBooking.getId(), createdUser.getId());
        checkBooking(receivedBooking, reqBody, createdItem, createdUser2, createdUser, BookingStatus.WAITING);
    }

    @Test
    void checkGetBookingByUserThatOwnerOfItem() throws Exception {
        //создание вещи
        RespItemDto createdItem = createItem(item, createdUser.getId());

        //создание брони на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        RespBookingDto createdBooking = createBooking(reqBody, createdUser2.getId());

        //получение созданной брони владельцем вещи и ее проверка
        RespBookingDto receivedBooking = getBookingById(createdBooking.getId(), createdUser.getId());
        checkBooking(receivedBooking, reqBody, createdItem, createdUser, createdUser2, BookingStatus.WAITING);
    }

    @Test
    void checkGetBookingByOtherUser() throws Exception {
        //создание юзера и вещи
        RespUserDto createdUser3 = createUser(user3);
        RespItemDto createdItem = createItem(item, createdUser.getId());

        //создание брони на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        RespBookingDto createdBooking = createBooking(reqBody, createdUser2.getId());

        //проверка недоступности получения вещи другим юзером (не владельцем вещи и не создателем брони)
        checkForbiddenError(getBookingByIdResAct(createdBooking.getId(), createdUser3.getId()), BOOKING_ACCESS_ERROR);
    }
}
