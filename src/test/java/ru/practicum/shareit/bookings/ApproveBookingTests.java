package ru.practicum.shareit.bookings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.practicum.shareit.GeneralAssertions;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.RespItemDto;
import ru.practicum.shareit.user.dto.RespUserDto;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.bookings.BookingData.booking;
import static ru.practicum.shareit.items.ItemData.item;
import static ru.practicum.shareit.users.UserData.*;

@DisplayName("Проверка одобрения/отклонения бронирования вещи")
public class ApproveBookingTests extends BookingTest {

    private static Stream<Arguments> getData() {
        return Stream.of(
                Arguments.of("true", BookingStatus.APPROVED),
                Arguments.of("false", BookingStatus.REJECTED)
        );
    }

    @ParameterizedTest
    @MethodSource("getData")
    void checkApproveBooking(String isApproved, BookingStatus status) throws Exception {
        //создание юзеров и вещи
        RespUserDto createdUser = createUser(user);
        RespUserDto createdUser2 = createUser(user2);
        RespItemDto createdItem = createItem(item, createdUser.getId());

        //создание брони на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        RespBookingDto createdBooking = createBooking(reqBody, createdUser2.getId());

        //одобрение брони
        RespBookingDto approvedBooking = approveBooking(createdBooking.getId(), isApproved, createdUser.getId());

        //проверка успшности одобрения брони
        GeneralAssertions.isEqualTo(approvedBooking.getStatus(), status,
                "Статус бронирования '%s' отличается от ожидаемого '%s'");
    }

    @Test
    void checkApproveByNotItemOwner() throws Exception {
        //создание юзеров и вещи
        RespUserDto createdUser = createUser(user);
        RespUserDto createdUser2 = createUser(user2);
        RespUserDto createdUser3 = createUser(user3);
        RespItemDto createdItem = createItem(item, createdUser.getId());

        //создание брони на вещь
        ReqBookingDto reqBody = booking.toBuilder().itemId(createdItem.getId()).build();
        RespBookingDto createdBooking = createBooking(reqBody, createdUser2.getId());

        //попытка одобрение брони и проверка отказа
        approveBookingResAct(createdBooking.getId(), "true", createdUser3.getId())
                .andExpect(status().isForbidden());
    }
}
