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
import static ru.practicum.shareit.constant.message.BookingValidMessages.USER_DO_NOT_HAVE_ANY_ITEM;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.*;

@DisplayName("Проверка получения бронирований вещей пользователя")
public class GetUserItemsBookingsTests extends BookingTest {
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
    void checkGetUserItemsBookings() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "ALL");
        for (RespBookingDto booking : userBookings) {
            checkBooking(booking, reqBody, createdItem, createdUser2, createdUser, BookingStatus.WAITING);
        }
    }

    @Test
    void checkGetUserItemsBookingsByBookingsOwner() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody, createdUser.getId());

        //проверка получеия ошибки
        checkNotFoundError(getUserItemsBookingsResAct(createdUser.getId(), "ALL"), USER_DO_NOT_HAVE_ANY_ITEM);
    }

    @Test
    void checkGetUserItemsBookingsWaiting() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "WAITING");
        GeneralAssertions.isEqualTo(userBookings.size(), 2,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }

    @Test
    void checkGetUserItemsBookingsRejected() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        RespBookingDto createdBooking = createBooking(reqBody, createdUser.getId());
        createBooking(reqBody, createdUser.getId());

        //отклонение брони
        approveBooking(createdBooking.getId(), "false", createdUser2.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "REJECTED");
        GeneralAssertions.isEqualTo(userBookings.size(), 1,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }

    @Test
    void checkGetUserItemsBookingsPast() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        ReqBookingDto reqBody2 = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody2, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "PAST");
        GeneralAssertions.isTrue(userBookings.isEmpty(), "Список броней не пустой!");
    }

    @Test
    void checkGetUserItemsBookingsFuture() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        ReqBookingDto reqBody2 = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody2, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "FUTURE");
        GeneralAssertions.isEqualTo(userBookings.size(), 2,
                "Размер списка броней '%d' не соответствует ожидаемому '%d'");
    }

    @Test
    void checkGetUserItemsBookingsCurrent() throws Exception {
        //создание броней на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        ReqBookingDto reqBody2 = booking.toBuilder().itemId(createdItem.getId()).build();
        createBooking(reqBody, createdUser.getId());
        createBooking(reqBody2, createdUser.getId());

        //получение и проверка броней
        Collection<RespBookingDto> userBookings = getUserItemsBookings(createdUser2.getId(), "CURRENT");
        GeneralAssertions.isTrue(userBookings.isEmpty(), "Список броней не пустой!");
    }
}
