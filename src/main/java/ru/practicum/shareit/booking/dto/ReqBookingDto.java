package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.marker.OnCreate;

import java.time.LocalDateTime;

import static ru.practicum.shareit.constant.message.BookingValidMessages.*;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Builder(toBuilder = true)
public class ReqBookingDto {
    @NotNull(groups = OnCreate.class, message = START_DATE_NULL_MESSAGE)
    private LocalDateTime start;

    @NotNull(groups = OnCreate.class, message = END_DATE_NULL_MESSAGE)
    private LocalDateTime end;

    @NotNull(groups = OnCreate.class, message = ITEM_ID_NULL_MESSAGE)
    private Long itemId;
}
