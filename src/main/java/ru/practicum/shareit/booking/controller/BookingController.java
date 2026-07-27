package ru.practicum.shareit.booking.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.marker.OnCreate;

import java.util.Collection;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public RespBookingDto getBookingById(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @PathVariable final Long bookingId
    ) {
        return bookingService.getBookingById(bookingId, userId);
    }

    @PostMapping
    public RespBookingDto create(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @Validated(OnCreate.class) @RequestBody final ReqBookingDto booking
    ) {
        return bookingService.create(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public void approveBooking(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @PathVariable final Long bookingId,
            @RequestParam final Boolean approved
    ) {
        bookingService.approveBooking(bookingId, approved, userId);
    }

    @GetMapping
    public Collection<RespBookingDto> getUserBookings(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @RequestParam(defaultValue = "ALL") final String state
    ) {
        return bookingService.getUserBookings(userId, state);
    }
}
