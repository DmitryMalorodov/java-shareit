package ru.practicum.shareit.bookings;

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

@DisplayName("Проверка получения бронирований вещей пользователя")
public class GetUserItemsBookingsTests extends BookingTest {

    @Test
    void checkGetUserItemsBookings() throws Exception {
        //создание юзеров и вещи
        RespUserDto createdUser = createUserDto(user);
        RespUserDto createdUser2 = createUserDto(user2);
        RespItemDto createdItem = createItemDto(item, createdUser2.getId());

        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBookingDto(reqBody, createdUser.getId());
        createBookingDto(reqBody, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "all");
        for (RespBookingDto booking : userBookings) {
            checkBooking(booking, reqBody, createdItem, createdUser2, createdUser, BookingStatus.WAITING);
        }
    }

    @Test
    void checkGetUserItemsBookingsByBookingsOwner() throws Exception {
        //создание юзеров и вещи
        RespUserDto createdUser = createUserDto(user);
        RespUserDto createdUser2 = createUserDto(user2);
        RespItemDto createdItem = createItemDto(item, createdUser2.getId());

        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBookingDto(reqBody, createdUser.getId());
        createBookingDto(reqBody, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser.getId(), "all");
        GeneralAssertions.isTrue(userBookings.isEmpty(), "Список броней не пустой!");
    }

    @Test
    void checkGetUserItemsBookingsWaiting() throws Exception {
        //создание юзеров и вещи
        RespUserDto createdUser = createUserDto(user);
        RespUserDto createdUser2 = createUserDto(user2);
        RespItemDto createdItem = createItemDto(item, createdUser2.getId());

        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBookingDto(reqBody, createdUser.getId());
        createBookingDto(reqBody, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "waiting");
        GeneralAssertions.isEqualTo(userBookings.size(), 2,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }

    @Test
    void checkGetUserItemsBookingsRejected() throws Exception {
        //создание юзеров и вещи
        RespUserDto createdUser = createUserDto(user);
        RespUserDto createdUser2 = createUserDto(user2);
        RespItemDto createdItem = createItemDto(item, createdUser2.getId());

        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        RespBookingDto createdBooking = createBookingDto(reqBody, createdUser.getId());
        createBookingDto(reqBody, createdUser.getId());

        //отклонение брони
        approveBooking(createdBooking.getId(), "false", createdUser2.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "rejected");
        GeneralAssertions.isEqualTo(userBookings.size(), 1,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }

    @Test
    void checkGetUserItemsBookingsPast() throws Exception {
        //создание юзеров и вещи
        RespUserDto createdUser = createUserDto(user);
        RespUserDto createdUser2 = createUserDto(user2);
        RespItemDto createdItem = createItemDto(item, createdUser2.getId());

        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        ReqBookingDto reqBody2 = booking3.toBuilder().itemId(createdItem.getId()).build();
        createBookingDto(reqBody, createdUser.getId());
        createBookingDto(reqBody2, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "past");
        GeneralAssertions.isEqualTo(userBookings.size(), 1,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }

    @Test
    void checkGetUserItemsBookingsFuture() throws Exception {
        //создание юзеров и вещи
        RespUserDto createdUser = createUserDto(user);
        RespUserDto createdUser2 = createUserDto(user2);
        RespItemDto createdItem = createItemDto(item, createdUser2.getId());

        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        ReqBookingDto reqBody2 = booking2.toBuilder().itemId(createdItem.getId()).build();
        createBookingDto(reqBody, createdUser.getId());
        createBookingDto(reqBody2, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "future");
        GeneralAssertions.isEqualTo(userBookings.size(), 1,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }

    @Test
    void checkGetUserItemsBookingsCurrent() throws Exception {
        //создание юзеров и вещи
        RespUserDto createdUser = createUserDto(user);
        RespUserDto createdUser2 = createUserDto(user2);
        RespItemDto createdItem = createItemDto(item, createdUser2.getId());

        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        ReqBookingDto reqBody2 = booking3.toBuilder().itemId(createdItem.getId()).build();
        createBookingDto(reqBody, createdUser.getId());
        createBookingDto(reqBody2, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "current");
        GeneralAssertions.isEqualTo(userBookings.size(), 1,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }
}
