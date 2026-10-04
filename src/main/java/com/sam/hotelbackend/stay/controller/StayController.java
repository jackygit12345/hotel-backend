package com.sam.hotelbackend.stay.controller;

import com.sam.hotelbackend.stay.dto.CheckInRequest;
import com.sam.hotelbackend.stay.dto.StayResponse;
import com.sam.hotelbackend.stay.entity.StayStatus;
import com.sam.hotelbackend.stay.service.StayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stays")
@RequiredArgsConstructor
public class StayController {

    private final StayService stayService;

    @PostMapping("/check-in")
    public ResponseEntity<StayResponse> checkIn(
            @Valid @RequestBody CheckInRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stayService.checkIn(request));
    }

    @GetMapping
    public ResponseEntity<List<StayResponse>> getAllStays() {

        return ResponseEntity.ok(
                stayService.getAllStays()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StayResponse> getStayById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                stayService.getStayById(id)
        );
    }

    @GetMapping("/by-reservation/{reservationId}")
    public ResponseEntity<StayResponse> getByReservation(
            @PathVariable Long reservationId) {

        return ResponseEntity.ok(
                stayService.getStayByReservationId(
                        reservationId
                )
        );
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<StayResponse>> getByStatus(
            @PathVariable StayStatus status) {

        return ResponseEntity.ok(
                stayService.getStaysByStatus(status)
        );
    }

    @GetMapping("/by-guest/{guestId}")
    public ResponseEntity<List<StayResponse>> getByGuest(
            @PathVariable Long guestId) {

        return ResponseEntity.ok(
                stayService.getStaysByGuest(guestId)
        );
    }

    @GetMapping("/by-room/{roomId}")
    public ResponseEntity<List<StayResponse>> getByRoom(
            @PathVariable Long roomId) {

        return ResponseEntity.ok(
                stayService.getStaysByRoom(roomId)
        );
    }

    @PostMapping("/{id}/check-out")
    public ResponseEntity<StayResponse> checkOut(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                stayService.checkOut(id)
        );
    }
}