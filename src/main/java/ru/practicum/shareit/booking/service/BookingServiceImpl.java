package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.AccessDeniedException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.shareit.booking.model.BookingStatus.*;
import static ru.practicum.shareit.constant.message.BookingValidMessages.*;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemService itemService;

    @Override
    public RespBookingDto getBookingById(Long id, Long userId) {
        RespBookingDto booking = bookingRepository.findById(id)
                .map(BookingMapper::toRespBookingDto)
                .orElseThrow(() -> new NotFoundException(String.format(BOOKING_NOT_FOUND_MESSAGE, id)));

        if (!booking.getItem().getOwner().getId().equals(userId) || !booking.getBooker().getId().equals(userId)) {
            throw new AccessDeniedException(BOOKING_ACCESS_ERROR);
        }

        return booking;
    }

    @Override
    public RespBookingDto create(ReqBookingDto booking, Long userId) {
        Item item = ItemMapper.toItem(itemService.getItemById(booking.getItemId()));
        User user = item.getOwner();
        Booking createdBooking = bookingRepository.save(BookingMapper.toBooking(booking, item, user));
        return BookingMapper.toRespBookingDto(createdBooking);
    }

    @Override
    public void approveBooking(Long bookingId, Boolean approved, Long userId) {
        Booking booking = BookingMapper.toBooking(getBookingById(bookingId, userId));

        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new AccessDeniedException(BOOKING_APPROVED_ACCESS_ERROR);
        }

        if (approved == true) {
            booking.setStatus(APPROVED);
        } else {
            booking.setStatus(REJECTED);
        }
        bookingRepository.save(booking);
    }

    //ДОБАВИТЬ СОРТИРОВКУ!!!!!!!!!!!!!!!!!
    @Override
    public Collection<RespBookingDto> getUserBookings(Long userId, String state) {
        switch (state) {
            case "ALL" -> {
                return BookingMapper.toRespBookingDto(bookingRepository.findByBookerId(userId));
            }

            case "CURRENT" -> {
                return BookingMapper.toRespBookingDto(
                        bookingRepository.findByBookerIdAndStartIsBetween_____(userId, LocalDateTime.now()));
            }

            case "PAST" -> {
                return BookingMapper.toRespBookingDto(
                        bookingRepository.findByBookerIdAndEndIsAfter(userId, LocalDateTime.now()));
            }

            case "FUTURE" -> {
                return BookingMapper.toRespBookingDto(
                        bookingRepository.findByBookerIdAndStartIsBefore(userId, LocalDateTime.now()));
            }

            case "WAITING", "REJECTED" -> {
                return BookingMapper.toRespBookingDto(bookingRepository.findByBookerIdAndStatusIs____(userId, state));
            }

            default -> throw new IllegalArgumentException("______________");
        }
    }

    @Override
    public Collection<RespBookingDto> getUserItemsBookings(Long userId, String state) {
        return List.of();
    }
}
