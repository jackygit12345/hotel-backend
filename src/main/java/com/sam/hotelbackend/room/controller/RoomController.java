package com.sam.hotelbackend.room.controller;

import com.sam.hotelbackend.room.dto.RoomRequest;
import com.sam.hotelbackend.room.dto.RoomResponse;
import com.sam.hotelbackend.room.entity.RoomStatus;
import com.sam.hotelbackend.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // ============================================================
    // CREATE
    // ============================================================

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
            @Valid @RequestBody RoomRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roomService.createRoom(request));
    }

    // ============================================================
    // GET BY ID
    // ============================================================

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                roomService.getRoomById(id)
        );
    }

    // ============================================================
    // GET ALL
    // ============================================================

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {

        return ResponseEntity.ok(
                roomService.getAllRooms()
        );
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomRequest request) {

        return ResponseEntity.ok(
                roomService.updateRoom(id, request)
        );
    }

    // ============================================================
    // DELETE
    // ============================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable Long id) {

        roomService.deleteRoom(id);

        return ResponseEntity.noContent().build();
    }

    // ============================================================
    // GET ROOMS BY ROOM TYPE
    // ============================================================

    @GetMapping("/by-type/{roomTypeId}")
    public ResponseEntity<List<RoomResponse>> getRoomsByRoomType(
            @PathVariable Long roomTypeId) {

        return ResponseEntity.ok(
                roomService.getRoomsByRoomType(roomTypeId)
        );
    }

    // ============================================================
    // GET ROOMS BY STATUS
    // ============================================================

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<RoomResponse>> getRoomsByStatus(
            @PathVariable RoomStatus status) {

        return ResponseEntity.ok(
                roomService.getRoomsByStatus(status)
        );
    }
}