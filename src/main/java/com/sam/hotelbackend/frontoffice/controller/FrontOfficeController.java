package com.sam.hotelbackend.frontoffice.controller;

import com.sam.hotelbackend.frontoffice.dto.FrontOfficeArrivalResponse;
import com.sam.hotelbackend.frontoffice.dto.FrontOfficeDepartureResponse;
import com.sam.hotelbackend.frontoffice.dto.FrontOfficeInHouseResponse;
import com.sam.hotelbackend.frontoffice.dto.FrontOfficeSummaryResponse;
import com.sam.hotelbackend.frontoffice.dto.RoomStatusResponse;
import com.sam.hotelbackend.frontoffice.service.FrontOfficeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/front-office")
@RequiredArgsConstructor
public class FrontOfficeController {

    private final FrontOfficeService frontOfficeService;

    // ============================================================
    // FRONT OFFICE SUMMARY
    // ============================================================

    @GetMapping("/summary")
    public ResponseEntity<FrontOfficeSummaryResponse> getSummary() {

        return ResponseEntity.ok(
                frontOfficeService.getSummary()
        );
    }

    // ============================================================
    // TODAY'S ARRIVALS
    // ============================================================

    @GetMapping("/arrivals")
    public ResponseEntity<List<FrontOfficeArrivalResponse>>
    getTodaysArrivals() {

        return ResponseEntity.ok(
                frontOfficeService.getTodaysArrivals()
        );
    }

    // ============================================================
    // TODAY'S DEPARTURES
    // ============================================================

    @GetMapping("/departures")
    public ResponseEntity<List<FrontOfficeDepartureResponse>>
    getTodaysDepartures() {

        return ResponseEntity.ok(
                frontOfficeService.getTodaysDepartures()
        );
    }

    // ============================================================
    // CURRENT IN-HOUSE GUESTS
    // ============================================================

    @GetMapping("/in-house")
    public ResponseEntity<List<FrontOfficeInHouseResponse>>
    getInHouseGuests() {

        return ResponseEntity.ok(
                frontOfficeService.getInHouseGuests()
        );
    }

    // ============================================================
    // AVAILABLE ROOMS
    // ============================================================

    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomStatusResponse>>
    getAvailableRooms() {

        return ResponseEntity.ok(
                frontOfficeService.getAvailableRooms()
        );
    }

    // ============================================================
    // OCCUPIED ROOMS
    // ============================================================

    @GetMapping("/occupied-rooms")
    public ResponseEntity<List<RoomStatusResponse>>
    getOccupiedRooms() {

        return ResponseEntity.ok(
                frontOfficeService.getOccupiedRooms()
        );
    }

    // ============================================================
    // ROOM STATUS
    // ============================================================

    @GetMapping("/room-status")
    public ResponseEntity<List<RoomStatusResponse>>
    getRoomStatus() {

        return ResponseEntity.ok(
                frontOfficeService.getRoomStatus()
        );
    }
}