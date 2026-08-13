package ru.practicum.shareit.booking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.exceptions.ValidationException;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final long userId,
            @PathVariable final Long bookingId
    ) {
        log.info("Get booking by id={}, userId={}", bookingId, userId);
        return bookingClient.getBooking(userId, bookingId);
    }

    @PostMapping
    public ResponseEntity<Object> create(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final long userId,
            @RequestBody @Valid final ReqBookingDto booking
    ) {
        log.info("Creating booking {}, userId={}", booking, userId);
        return bookingClient.bookItem(userId, booking);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @PathVariable final Long bookingId,
            @RequestParam final Boolean approved
    ) {
        log.info("Approve/reject booking by id={}, approved={}, userId={}", bookingId, approved, userId);
        return bookingClient.approveBooking(bookingId, approved, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserBookings(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final long userId,
            @RequestParam(name = "state", defaultValue = "all") final String stateParam
    ) {
        BookingState state = BookingState.from(stateParam)
                .orElseThrow(() -> new ValidationException("Unknown state: " + stateParam));
        log.info("Get user bookings with state {}, userId={}", stateParam, userId);
        return bookingClient.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getUserItemsBookings(
            @RequestHeader("X-Sharer-User-Id")
            @NotNull
            @Min(value = 1, message = "ID пользователя должен быть больше 0")
            final Long userId,
            @RequestParam(name = "state", defaultValue = "all") final String stateParam
    ) {
        BookingState state = BookingState.from(stateParam)
                .orElseThrow(() -> new ValidationException("Unknown state: " + stateParam));
        log.info("Get user items bookings with state {}, userId={}", stateParam, userId);
        return bookingClient.getUserItemsBookings(userId, state);
    }
}
