package com.sam.hotelbackend.reservation.mapper;

import com.sam.hotelbackend.guest.entity.Guest;
import com.sam.hotelbackend.reservation.dto.ReservationRequest;
import com.sam.hotelbackend.reservation.dto.ReservationResponse;
import com.sam.hotelbackend.reservation.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    // ============================================================
    // REQUEST → ENTITY
    // ============================================================

    public Reservation toEntity(ReservationRequest request, Guest guest) {

        Reservation reservation = new Reservation();

        reservation.setGuest(guest);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setAdults(request.getAdults());
        reservation.setChildren(request.getChildren());
        reservation.setRoomType(request.getRoomType());
        reservation.setSource(request.getSource());
        reservation.setSpecialRequests(request.getSpecialRequests());
        reservation.setNotes(request.getNotes());

        return reservation;
    }

    // ============================================================
    // ENTITY → RESPONSE
    // ============================================================

    public ReservationResponse toResponse(Reservation reservation) {

        ReservationResponse response = new ReservationResponse();

        response.setId(reservation.getId());
        response.setReservationNumber(reservation.getReservationNumber());

        // Guest information
        response.setGuestId(reservation.getGuest().getId());

        response.setGuestName(
                reservation.getGuest().getFirstName()
                        + " "
                        + reservation.getGuest().getLastName()
        );

        // Stay dates
        response.setCheckInDate(reservation.getCheckInDate());
        response.setCheckOutDate(reservation.getCheckOutDate());

        // Guest counts
        response.setAdults(reservation.getAdults());
        response.setChildren(reservation.getChildren());

        // Room
        response.setRoomType(reservation.getRoomType());

        // Reservation
        response.setStatus(reservation.getStatus());
        response.setSource(reservation.getSource());

        // Additional information
        response.setSpecialRequests(reservation.getSpecialRequests());
        response.setNotes(reservation.getNotes());

        // Audit
        response.setCreatedAt(reservation.getCreatedAt());
        response.setUpdatedAt(reservation.getUpdatedAt());

        return response;
    }
}