package ru.practicum.shareit.booking.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.ReqBookingDto;
import ru.practicum.shareit.booking.dto.RespBookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingMapper {

    public static Booking toBooking(ReqBookingDto reqBookingDto, Item item, User user) {
        return Booking.builder()
                .start(reqBookingDto.getStart())
                .end(reqBookingDto.getEnd())
                .item(item)
                .booker(user)
                .build();
    }

    public static Booking toBooking(RespBookingDto respBookingDto) {
        return Booking.builder()
                .id(respBookingDto.getId())
                .start(respBookingDto.getStart())
                .end(respBookingDto.getEnd())
                .item(respBookingDto.getItem())
                .booker(respBookingDto.getBooker())
                .status(respBookingDto.getStatus())
                .build();
    }

    public static RespBookingDto toRespBookingDto(Booking booking) {
        return RespBookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(booking.getItem())
                .booker(booking.getBooker())
                .status(booking.getStatus())
                .build();
    }

    public static List<RespBookingDto> toRespBookingDto(Collection<Booking> bookings) {
        return bookings.stream()
                .map(BookingMapper::toRespBookingDto)
                .toList();
    }
}
