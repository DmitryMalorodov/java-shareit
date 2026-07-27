package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.util.Collection;

public interface BookingService {
    RespBookingDto getBookingById(Long id, Long userId);

    RespBookingDto create(ReqBookingDto booking, Long userId);

    void approveBooking(Long bookingId, Boolean approved, Long userId);

    //получение списка бронирований, которые сделал пользователь
    Collection<RespBookingDto> getUserBookings(Long userId, BookingStatus state);

    //получения списка бронирований вещей пользователя
    Collection<RespBookingDto> getUserItemsBookings(Long userId, String state);
}