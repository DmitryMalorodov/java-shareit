package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.AccessDeniedException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.practicum.shareit.booking.model.BookingStatus.APPROVED;
import static ru.practicum.shareit.booking.model.BookingStatus.REJECTED;
import static ru.practicum.shareit.constant.message.BookingValidMessages.*;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public RespBookingDto getBookingById(Long id, Long userId) {
        RespBookingDto booking = bookingRepository.findById(id)
                .map(BookingMapper::toRespBookingDto)
                .orElseThrow(() -> new NotFoundException(String.format(BOOKING_NOT_FOUND_MESSAGE, id)));

        if (!booking.getItem().getOwner().getId().equals(userId) && !booking.getBooker().getId().equals(userId)) {
            throw new AccessDeniedException(BOOKING_ACCESS_ERROR);
        }

        return booking;
    }

    @Override
    public RespBookingDto create(ReqBookingDto booking, Long userId) {
        Item item = ItemMapper.toItem(itemService.getItemById(booking.getItemId(), userId));
        User user = UserMapper.toUser(userService.getUserById(userId));

        if (!item.getAvailable()) {
            throw new ValidationException("Невозможно забронировать недоступную вещь!");
        }

        Booking createdBooking = bookingRepository.save(BookingMapper.toBooking(booking, item, user));
        return BookingMapper.toRespBookingDto(createdBooking);
    }

    @Override
    public RespBookingDto approveBooking(Long bookingId, Boolean approved, Long userId) {
        Booking booking = BookingMapper.toBooking(getBookingById(bookingId, userId));

        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new AccessDeniedException(BOOKING_APPROVED_ACCESS_ERROR);
        }

        if (approved == true) {
            booking.setStatus(APPROVED);
        } else {
            booking.setStatus(REJECTED);
        }

        Booking createdBooking = bookingRepository.save(booking);
        return BookingMapper.toRespBookingDto(createdBooking);
    }

    @Override
    public Collection<RespBookingDto> getUserBookings(Long userId, BookingState state) {
        switch (state) {
            case ALL -> {
                return BookingMapper.toRespBookingDto(bookingRepository.findByBookerIdOrderByStartDesc(userId));
            }

            case CURRENT -> {
                return BookingMapper.toRespBookingDto(
                        bookingRepository.findByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(
                                userId, LocalDateTime.now(), LocalDateTime.now()));
            }

            case PAST -> {
                return BookingMapper.toRespBookingDto(
                        bookingRepository.findByBookerIdAndEndIsBeforeOrderByStartDesc(userId, LocalDateTime.now()));
            }

            case FUTURE -> {
                return BookingMapper.toRespBookingDto(
                        bookingRepository.findByBookerIdAndStartIsAfterOrderByStartDesc(userId, LocalDateTime.now()));
            }

            case WAITING -> {
                return BookingMapper.toRespBookingDto(bookingRepository
                        .findByBookerIdAndStatusOrderByStartDesc(userId, BookingStatus.WAITING));
            }

            case REJECTED -> {
                return BookingMapper.toRespBookingDto(bookingRepository
                        .findByBookerIdAndStatusOrderByStartDesc(userId, REJECTED));
            }

            default -> throw new ValidationException("Передано несуществуюшее значение состояния заказа");
        }
    }

    @Override
    public Collection<RespBookingDto> getUserItemsBookings(Long userId, BookingState state) {
        if (itemService.getUserItems(userId).isEmpty()) {
            throw new NotFoundException("У пользователя нет ни одной вещи!");
        }

        switch (state) {
            case ALL -> {
                return BookingMapper.toRespBookingDto(bookingRepository
                        .findByItemOwnerIdOrderByStartDesc(userId));
            }

            case CURRENT -> {
                return BookingMapper.toRespBookingDto(
                        bookingRepository.findByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(
                                userId, LocalDateTime.now(), LocalDateTime.now()));
            }

            case PAST -> {
                return BookingMapper.toRespBookingDto(
                        bookingRepository.findByItemOwnerIdAndEndIsBeforeOrderByStartDesc(userId, LocalDateTime.now()));
            }

            case FUTURE -> {
                return BookingMapper.toRespBookingDto(
                        bookingRepository.findByItemOwnerIdAndStartIsAfterOrderByStartDesc(userId, LocalDateTime.now()));
            }

            case WAITING -> {
                return BookingMapper.toRespBookingDto(bookingRepository
                        .findByItemOwnerIdAndStatusOrderByStartDesc(userId, BookingStatus.WAITING));
            }

            case REJECTED -> {
                return BookingMapper.toRespBookingDto(bookingRepository
                        .findByItemOwnerIdAndStatusOrderByStartDesc(userId, BookingStatus.REJECTED));
            }

            default -> throw new ValidationException("Передано несуществуюшее значение состояния заказа");
        }
    }
}
