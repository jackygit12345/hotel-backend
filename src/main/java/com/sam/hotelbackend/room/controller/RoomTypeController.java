package com.sam.hotelbackend.room.controller;

import com.sam.hotelbackend.room.dto.RoomTypeRequest;
import com.sam.hotelbackend.room.dto.RoomTypeResponse;
import com.sam.hotelbackend.room.service.RoomTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-types")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    // ============================================================
    // CREATE
    // ============================================================

    @PostMapping
    public ResponseEntity<RoomTypeResponse> createRoomType(
            @Valid @RequestBody RoomTypeRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roomTypeService.createRoomType(request));
    }

    // ============================================================
    // GET BY ID
    // ============================================================

    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeResponse> getRoomTypeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                roomTypeService.getRoomTypeById(id)
        );
    }

    // ============================================================
    // GET ALL
    // ============================================================

    @GetMapping
    public ResponseEntity<List<RoomTypeResponse>> getAllRoomTypes() {

        return ResponseEntity.ok(
                roomTypeService.getAllRoomTypes()
        );
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeResponse> updateRoomType(
            @PathVariable Long id,
            @Valid @RequestBody RoomTypeRequest request) {

        return ResponseEntity.ok(
                roomTypeService.updateRoomType(id, request)
        );
    }

    // ============================================================
    // DELETE
    // ============================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomType(
            @PathVariable Long id) {

        roomTypeService.deleteRoomType(id);

        return ResponseEntity.noContent().build();
    }
}