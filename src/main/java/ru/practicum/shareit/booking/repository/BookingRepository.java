package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Collection<Booking> findByBookerIdAndEndIsAfter(Long bookerId, LocalDateTime end);

    Collection<Booking> findByBookerIdAndStartIsBefore(Long bookerId, LocalDateTime start);

    Collection<Booking> findByBookerId(Long bookerId);

    Collection<Booking> findByBookerIdAndStatusIs____(Long bookerId, String status);

    Collection<Booking> findByBookerIdAndStartIsBetween_____(Long bookerId, LocalDateTime start);

    @Query("select b from Booking as b " +
            "join b.item as i " +
            "where i.owner.id = ?1")
    Collection<Booking> findByItemOwnerId(Long ownerId);
}
