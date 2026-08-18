package ru.practicum.shareit.bookings;

import ru.practicum.shareit.booking.dto.ReqBookingDto;

import java.time.LocalDateTime;

public class BookingData {
    public static final ReqBookingDto booking = ReqBookingDto.builder()
            .start(LocalDateTime.now().plusDays(1))
            .end(LocalDateTime.now().plusDays(5))
            .build();

    public static final ReqBookingDto booking2 = ReqBookingDto.builder()
            .start(LocalDateTime.now().plusDays(1))
            .end(LocalDateTime.now().plusDays(5))
            .itemId(1L)
            .build();
}
