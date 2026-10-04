package com.sam.hotelbackend.reservation.repository;

import com.sam.hotelbackend.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByReservationNumber(String reservationNumber);

    boolean existsByReservationNumber(String reservationNumber);

    // ============================================================
    // FRONT OFFICE - ARRIVALS / DEPARTURES
    // ============================================================
    List<Reservation> findByCheckInDate(LocalDate checkInDate);
    List<Reservation> findByCheckOutDate(LocalDate checkOutDate);

// ============================================================
// FRONT OFFICE - SEARCH
// ============================================================

List<Reservation> findByReservationNumberContainingIgnoreCase(
        String reservationNumber
);

List<Reservation> findByGuestId(Long guestId);

// ============================================================
// FRONT OFFICE - ROOM AVAILABILITY
// ============================================================

List<Reservation> findByRoomTypeAndCheckInDateLessThanAndCheckOutDateGreaterThan(
        String roomType,
        LocalDate checkOutDate,
        LocalDate checkInDate
);
}