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
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.user;

@DisplayName("Проверка создания бронирования вещи")
public class CreateBookingTests extends BookingTest {
    private RespUserDto createdUser;
    private RespItemDto createdItem;

    @BeforeEach
    void setUp() throws Exception {
        createdUser = createUser(user);
        createdItem = createItem(item, createdUser.getId());
    }

    @Test
    void checkCreateItem() throws Exception {
        //создание брони вещи
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        RespBookingDto createdBooking = createBooking(reqBody, createdUser.getId());

        //проверка соданной брони
        checkBooking(createdBooking, reqBody, createdItem, createdUser, createdUser, BookingStatus.WAITING);
    }
}
