package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Builder(toBuilder = true)
public class ReqBookingDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private Long itemId;
}
