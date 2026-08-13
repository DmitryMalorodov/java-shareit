package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static ru.practicum.shareit.constant.message.BookingValidMessages.*;

@Data
@Builder(toBuilder = true)
public class ReqBookingDto {
    @NotNull(message = START_DATE_NULL_MESSAGE)
    @FutureOrPresent(message = BOOKING_START_DATE_VALID_MESSAGE)
    private LocalDateTime start;

    @NotNull(message = END_DATE_NULL_MESSAGE)
    @Future(message = BOOKING_END_DATE_VALID_MESSAGE)
    private LocalDateTime end;

    @NotNull(message = ITEM_ID_NULL_MESSAGE)
    private Long itemId;
}
