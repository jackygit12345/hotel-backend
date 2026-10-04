package com.sam.hotelbackend.frontoffice.controller;

import com.sam.hotelbackend.frontoffice.dto.GuestSearchResponse;
import com.sam.hotelbackend.frontoffice.dto.ReservationSearchResponse;
import com.sam.hotelbackend.frontoffice.dto.RoomAvailabilityResponse;
import com.sam.hotelbackend.frontoffice.service.FrontOfficeSearchService;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/front-office/search")
@RequiredArgsConstructor
public class FrontOfficeSearchController {

    private final FrontOfficeSearchService frontOfficeSearchService;

    // ============================================================
    // SEARCH GUESTS
    // ============================================================

    @GetMapping("/guests")
    public ResponseEntity<List<GuestSearchResponse>> searchGuests(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                frontOfficeSearchService.searchGuests(keyword)
        );
    }

    // ============================================================
    // SEARCH RESERVATIONS
    // ============================================================

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationSearchResponse>>
    searchReservations(
            @RequestParam String reservationNumber) {

        return ResponseEntity.ok(
                frontOfficeSearchService
                        .searchReservations(reservationNumber)
        );
    }

    // ============================================================
    // RESERVATIONS BY GUEST
    // ============================================================

    @GetMapping("/reservations/by-guest/{guestId}")
    public ResponseEntity<List<ReservationSearchResponse>>
    getReservationsByGuest(
            @PathVariable Long guestId) {

        return ResponseEntity.ok(
                frontOfficeSearchService
                        .getReservationsByGuest(guestId)
        );
    }

    // ============================================================
    // ROOM AVAILABILITY
    // ============================================================

    @GetMapping("/availability/rooms")
    public ResponseEntity<List<RoomAvailabilityResponse>>
    getRoomAvailability(

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkInDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkOutDate) {

        return ResponseEntity.ok(
                frontOfficeSearchService
                        .getRoomAvailability(
                                checkInDate,
                                checkOutDate
                        )
        );
    }
}