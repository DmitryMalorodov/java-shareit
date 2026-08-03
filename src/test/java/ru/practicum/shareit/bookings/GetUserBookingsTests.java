package ru.practicum.shareit.bookings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.GeneralAssertions;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.Collection;

import static ru.practicum.shareit.bookings.BookingData.*;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.user;
import static ru.practicum.shareit.users.UserData.user2;

@DisplayName("Проверка получения бронирований пользователя")
public class GetUserBookingsTests extends BookingTest {
    private RespUserDto createdUser;
    private RespUserDto createdUser2;
    private RespItemDto createdItem;

    @BeforeEach
    void setUp() throws Exception {
        createdUser = createUser(user);
        createdUser2 = createUser(user2);
        createdItem = createItem(item, createdUser2.getId());
    }

    @Test
    void checkGetUserBookings() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserBookings(createdUser.getId(), "ALL");
        for (RespBookingDto booking : userBookings) {
            checkBooking(booking, reqBody, createdItem, createdUser2, createdUser, BookingStatus.WAITING);
        }
    }

    @Test
    void checkGetUserBookingsByItemOwner() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserBookings(createdUser2.getId(), "ALL");
        GeneralAssertions.isTrue(userBookings.isEmpty(), "Список броней не пустой!");
    }

    @Test
    void checkGetUserBookingsWaiting() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserBookings(createdUser.getId(), "WAITING");
        GeneralAssertions.isEqualTo(userBookings.size(), 2,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }

    @Test
    void checkGetUserBookingsPast() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserBookings(createdUser.getId(), "PAST");
        GeneralAssertions.isEqualTo(userBookings.size(), 2,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }

    @Test
    void checkGetUserBookingsFuture() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        ReqBookingDto reqBody2 = booking2.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody2, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserBookings(createdUser.getId(), "FUTURE");
        GeneralAssertions.isEqualTo(userBookings.size(), 1,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }

    @Test
    void checkGetUserBookingsCurrent() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        ReqBookingDto reqBody2 = booking3.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody2, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserBookings(createdUser.getId(), "CURRENT");
        GeneralAssertions.isEqualTo(userBookings.size(), 1,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }
}
