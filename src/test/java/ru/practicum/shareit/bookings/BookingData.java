package ru.practicum.shareit.bookings;

import ru.practicum.shareit.booking.dto.ReqBookingDto;

import java.time.LocalDateTime;

public class BookingData {
    public static final ReqBookingDto booking = ReqBookingDto.builder()
            .start(LocalDateTime.now().minusDays(3))
            .end(LocalDateTime.now().minusDays(1))
            .build();
}
