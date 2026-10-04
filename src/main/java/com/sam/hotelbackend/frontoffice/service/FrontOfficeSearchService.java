package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.frontoffice.dto.GuestSearchResponse;
import com.sam.hotelbackend.frontoffice.dto.ReservationSearchResponse;
import com.sam.hotelbackend.frontoffice.dto.RoomAvailabilityResponse;

import java.time.LocalDate;
import java.util.List;

public interface FrontOfficeSearchService {

    List<GuestSearchResponse> searchGuests(String keyword);

    List<ReservationSearchResponse> searchReservations(
            String reservationNumber
    );

    List<ReservationSearchResponse> getReservationsByGuest(
            Long guestId
    );

    List<RoomAvailabilityResponse> getRoomAvailability(
            LocalDate checkInDate,
            LocalDate checkOutDate
    );
}