package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.Collection;

@FunctionalInterface
public interface BookingFinder {
    Collection<Booking> find(Long userId, LocalDateTime time);
}
