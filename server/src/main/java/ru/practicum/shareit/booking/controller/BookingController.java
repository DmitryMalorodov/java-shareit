package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collection;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public RespBookingDto getBookingById(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @PathVariable final Long bookingId
    ) {
        return bookingService.getBookingById(bookingId, userId);
    }

    @PostMapping
    public RespBookingDto create(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @RequestBody final ReqBookingDto booking
    ) {
        return bookingService.create(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public RespBookingDto approveBooking(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @PathVariable final Long bookingId,
            @RequestParam final Boolean approved
    ) {
        return bookingService.approveBooking(bookingId, approved, userId);
    }

    @GetMapping
    public Collection<RespBookingDto> getUserBookings(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @RequestParam(defaultValue = "ALL") final BookingState state
    ) {
        return bookingService.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public Collection<RespBookingDto> getUserItemsBookings(
            @RequestHeader("X-Sharer-User-Id") final Long userId,
            @RequestParam(defaultValue = "ALL") final BookingState state
    ) {
        return bookingService.getUserItemsBookings(userId, state);
    }
}
