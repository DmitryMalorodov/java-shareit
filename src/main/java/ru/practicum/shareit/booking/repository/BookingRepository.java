package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Collection<Booking> findByBookerIdAndEndIsBeforeOrderByStartDesc(Long bookerId, LocalDateTime end);

    Collection<Booking> findByBookerIdAndStartIsAfterOrderByStartDesc(Long bookerId, LocalDateTime start);

    Collection<Booking> findByBookerIdOrderByStartDesc(Long bookerId);

    Collection<Booking> findByBookerIdAndStatusOrderByStartDesc(Long bookerId, BookingState status);

    Collection<Booking> findByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(
            Long bookerId, LocalDateTime now1, LocalDateTime now2);

    @Query("select b from Booking as b " +
            "join b.item as i " +
            "where i.owner.id = ?1")
    Collection<Booking> findByItemOwnerId(Long ownerId);
}
