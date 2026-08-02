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
import static ru.practicum.shareit.constant.message.BookingValidMessages.*;
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

    @Test
    void checkStartNullValidation() throws Exception {
        ReqBookingDto startNull = booking.toBuilder()
                .itemId(createdItem.getId())
                .start(null)
                .build();

        checkValidationError(createBookingResAct(startNull, createdUser.getId()), START_DATE_NULL_MESSAGE);
    }

    @Test
    void checkEndNullValidation() throws Exception {
        ReqBookingDto endNull = booking.toBuilder()
                .itemId(createdItem.getId())
                .end(null)
                .build();

        checkValidationError(createBookingResAct(endNull, createdUser.getId()), END_DATE_NULL_MESSAGE);
    }

    @Test
    void checkItemIdNullValidation() throws Exception {
        checkValidationError(createBookingResAct(booking, createdUser.getId()), ITEM_ID_NULL_MESSAGE);
    }
}
