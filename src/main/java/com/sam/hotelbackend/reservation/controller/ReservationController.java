package com.sam.hotelbackend.reservation.controller;

import com.sam.hotelbackend.reservation.dto.ReservationRequest;
import com.sam.hotelbackend.reservation.dto.ReservationResponse;
import com.sam.hotelbackend.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // ============================================================
    // CREATE RESERVATION
    // ============================================================

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationRequest request) {

        ReservationResponse response =
                reservationService.createReservation(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ============================================================
    // GET RESERVATION BY ID
    // ============================================================

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservationService.getReservationById(id)
        );
    }

    // ============================================================
    // GET ALL RESERVATIONS
    // ============================================================

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {

        return ResponseEntity.ok(
                reservationService.getAllReservations()
        );
    }

    // ============================================================
    // UPDATE RESERVATION
    // ============================================================

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequest request) {

        return ResponseEntity.ok(
                reservationService.updateReservation(id, request)
        );
    }

    // ============================================================
    // DELETE RESERVATION
    // ============================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id) {

        reservationService.deleteReservation(id);

        return ResponseEntity.noContent().build();
    }

// ============================================================
// CONFIRM RESERVATION
// ============================================================

@PostMapping("/{id}/confirm")
public ResponseEntity<Void> confirmReservation(
        @PathVariable Long id) {

    reservationService.confirmReservation(id);

    return ResponseEntity.noContent().build();
}

// ============================================================
// CANCEL RESERVATION
// ============================================================

@PostMapping("/{id}/cancel")
public ResponseEntity<Void> cancelReservation(
        @PathVariable Long id) {

    reservationService.cancelReservation(id);

    return ResponseEntity.noContent().build();
}

// ============================================================
// CHECK-IN RESERVATION
// ============================================================

@PostMapping("/{id}/check-in")
public ResponseEntity<Void> checkInReservation(
        @PathVariable Long id) {

    reservationService.checkInReservation(id);

    return ResponseEntity.noContent().build();
}

// ============================================================
// CHECK-OUT RESERVATION
// ============================================================

@PostMapping("/{id}/check-out")
public ResponseEntity<Void> checkOutReservation(
        @PathVariable Long id) {

    reservationService.checkOutReservation(id);

    return ResponseEntity.noContent().build();
}


@PostMapping("/{id}/no-show")
public ResponseEntity<Void> markAsNoShow(
        @PathVariable Long id) {

    reservationService.markAsNoShow(id);

    return ResponseEntity.noContent().build();
}

}