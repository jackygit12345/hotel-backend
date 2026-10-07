package com.sam.hotelbackend.stay.controller;

import com.sam.hotelbackend.stay.dto.CheckInRequest;
import com.sam.hotelbackend.stay.dto.StayResponse;
import com.sam.hotelbackend.stay.dto.StayExtensionRequest;
import com.sam.hotelbackend.stay.dto.RoomTransferRequest;
import com.sam.hotelbackend.stay.dto.EarlyCheckInRequest;
import com.sam.hotelbackend.stay.dto.LateCheckOutRequest;
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

    @PostMapping("/{id}/extend")
public ResponseEntity<StayResponse> extendStay(
        @PathVariable Long id,
        @Valid @RequestBody StayExtensionRequest request) {

    return ResponseEntity.ok(
            stayService.extendStay(id, request)
    );
}


@PostMapping("/{id}/transfer-room")
public ResponseEntity<StayResponse> transferRoom(
        @PathVariable Long id,
        @Valid @RequestBody RoomTransferRequest request) {

    return ResponseEntity.ok(
            stayService.transferRoom(id, request)
    );
}


    @PostMapping("/{id}/check-out")
    public ResponseEntity<StayResponse> checkOut(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                stayService.checkOut(id)
        );
    }


    @PostMapping("/{id}/early-check-in")
	public ResponseEntity<StayResponse> recordEarlyCheckIn(
        @PathVariable Long id,
        @Valid @RequestBody EarlyCheckInRequest request) {

    return ResponseEntity.ok(
            stayService.recordEarlyCheckIn(id, request)
    	);
	}

    @PostMapping("/{id}/late-check-out")
	public ResponseEntity<StayResponse> recordLateCheckOut(
        @PathVariable Long id,
        @Valid @RequestBody LateCheckOutRequest request) {

    return ResponseEntity.ok(
            stayService.recordLateCheckOut(id, request)
    	);
	}

}