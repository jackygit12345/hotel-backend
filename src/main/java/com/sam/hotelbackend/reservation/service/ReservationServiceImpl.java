package com.sam.hotelbackend.reservation.service;

import com.sam.hotelbackend.guest.entity.Guest;
import com.sam.hotelbackend.guest.repository.GuestRepository;
import com.sam.hotelbackend.reservation.dto.ReservationRequest;
import com.sam.hotelbackend.reservation.dto.ReservationResponse;
import com.sam.hotelbackend.reservation.entity.Reservation;
import com.sam.hotelbackend.reservation.entity.ReservationStatus;
import com.sam.hotelbackend.reservation.exception.ReservationNotFoundException;
import com.sam.hotelbackend.reservation.mapper.ReservationMapper;
import com.sam.hotelbackend.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final ReservationMapper reservationMapper;

    // ============================================================
    // CREATE RESERVATION
    // ============================================================

    @Override
    public ReservationResponse createReservation(ReservationRequest request) {

        validateStayDates(request);

        Guest guest = guestRepository.findById(request.getGuestId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Guest not found with id: " + request.getGuestId()
                        )
                );

        Reservation reservation =
                reservationMapper.toEntity(request, guest);

        reservation.setReservationNumber(
                generateReservationNumber()
        );

        Reservation savedReservation =
                reservationRepository.save(reservation);

        return reservationMapper.toResponse(savedReservation);
    }

    // ============================================================
    // GET RESERVATION
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public ReservationResponse getReservationById(Long id) {

        Reservation reservation = findReservation(id);

        return reservationMapper.toResponse(reservation);
    }

    // ============================================================
    // GET ALL RESERVATIONS
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getAllReservations() {

        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    // ============================================================
    // UPDATE RESERVATION
    // ============================================================

    @Override
    public ReservationResponse updateReservation(
            Long id,
            ReservationRequest request) {

        validateStayDates(request);

        Reservation reservation = findReservation(id);

        Guest guest = guestRepository.findById(request.getGuestId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Guest not found with id: " + request.getGuestId()
                        )
                );

        reservation.setGuest(guest);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setAdults(request.getAdults());
        reservation.setChildren(request.getChildren());
        reservation.setRoomType(request.getRoomType());
        reservation.setSource(request.getSource());
        reservation.setSpecialRequests(request.getSpecialRequests());
        reservation.setNotes(request.getNotes());

        return reservationMapper.toResponse(reservation);
    }

    // ============================================================
    // DELETE RESERVATION
    // ============================================================

    @Override
    public void deleteReservation(Long id) {

        Reservation reservation = findReservation(id);

        reservationRepository.delete(reservation);
    }

// ============================================================
// CONFIRM RESERVATION
// ============================================================

@Override
public void confirmReservation(Long id) {

    Reservation reservation = findReservation(id);

    if (reservation.getStatus()
            != ReservationStatus.PENDING) {

        throw new IllegalStateException(
                "Only PENDING reservations can be confirmed."
        );
    }

    reservation.setStatus(ReservationStatus.CONFIRMED);
}

// ============================================================
// CANCEL RESERVATION
// ============================================================

@Override
public void cancelReservation(Long id) {

    Reservation reservation = findReservation(id);

    if (reservation.getStatus()
            != ReservationStatus.PENDING
            && reservation.getStatus()
            != ReservationStatus.CONFIRMED) {

        throw new IllegalStateException(
                "Only PENDING or CONFIRMED reservations can be cancelled."
        );
    }

    reservation.setStatus(ReservationStatus.CANCELLED);
}

// ============================================================
// CHECK-IN RESERVATION
// ============================================================

@Override
public void checkInReservation(Long id) {

    Reservation reservation = findReservation(id);

    if (reservation.getStatus()
            != ReservationStatus.CONFIRMED) {

        throw new IllegalStateException(
                "Only CONFIRMED reservations can be checked in."
        );
    }

    reservation.setStatus(ReservationStatus.CHECKED_IN);
}

// ============================================================
// CHECK-OUT RESERVATION
// ============================================================

@Override
public void checkOutReservation(Long id) {

    Reservation reservation = findReservation(id);

    if (reservation.getStatus()
            != ReservationStatus.CHECKED_IN) {

        throw new IllegalStateException(
                "Only CHECKED_IN reservations can be checked out."
        );
    }

    reservation.setStatus(ReservationStatus.CHECKED_OUT);
}




    // ============================================================
    // FIND RESERVATION
    // ============================================================

    private Reservation findReservation(Long id) {

        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ReservationNotFoundException(id)
                );
    }

    // ============================================================
    // VALIDATE STAY DATES
    // ============================================================

    private void validateStayDates(ReservationRequest request) {

        LocalDate checkIn = request.getCheckInDate();
        LocalDate checkOut = request.getCheckOutDate();

        if (checkIn != null
                && checkOut != null
                && !checkIn.isBefore(checkOut)) {

            throw new IllegalArgumentException(
                    "Check-in date must be before check-out date."
            );
        }
    }

    // ============================================================
    // GENERATE RESERVATION NUMBER
    // ============================================================

    private String generateReservationNumber() {

        String datePart = LocalDate.now()
                .format(DateTimeFormatter.BASIC_ISO_DATE);

        long nextNumber = reservationRepository.count() + 1;

        String reservationNumber = String.format(
                "RES-%s-%04d",
                datePart,
                nextNumber
        );

        while (reservationRepository
                .existsByReservationNumber(reservationNumber)) {

            nextNumber++;
            reservationNumber = String.format(
                    "RES-%s-%04d",
                    datePart,
                    nextNumber
            );
        }

        return reservationNumber;
    }

// ============================================================
// MARK RESERVATION AS NO-SHOW
// ============================================================

@Override
public void markAsNoShow(Long id) {

    Reservation reservation = findReservation(id);

    if (reservation.getStatus()
            != ReservationStatus.CONFIRMED) {

        throw new IllegalStateException(
                "Only CONFIRMED reservations can be marked as NO-SHOW."
        );
    }

    reservation.setStatus(ReservationStatus.NO_SHOW);
}

}