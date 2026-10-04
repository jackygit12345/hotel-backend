package com.sam.hotelbackend.room.mapper;

import com.sam.hotelbackend.room.dto.RoomRequest;
import com.sam.hotelbackend.room.dto.RoomResponse;
import com.sam.hotelbackend.room.dto.RoomTypeRequest;
import com.sam.hotelbackend.room.dto.RoomTypeResponse;
import com.sam.hotelbackend.room.entity.Room;
import com.sam.hotelbackend.room.entity.RoomType;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    // ============================================================
    // ROOM TYPE REQUEST → ENTITY
    // ============================================================

    public RoomType toRoomTypeEntity(RoomTypeRequest request) {

        RoomType roomType = new RoomType();

        roomType.setName(request.getName());
        roomType.setDescription(request.getDescription());
        roomType.setMaxAdults(request.getMaxAdults());
        roomType.setMaxChildren(request.getMaxChildren());
        roomType.setBasePrice(request.getBasePrice());

        if (request.getStatus() != null) {
            roomType.setStatus(request.getStatus());
        }

        return roomType;
    }

    // ============================================================
    // ROOM TYPE ENTITY → RESPONSE
    // ============================================================

    public RoomTypeResponse toRoomTypeResponse(
            RoomType roomType) {

        RoomTypeResponse response = new RoomTypeResponse();

        response.setId(roomType.getId());
        response.setName(roomType.getName());
        response.setDescription(roomType.getDescription());
        response.setMaxAdults(roomType.getMaxAdults());
        response.setMaxChildren(roomType.getMaxChildren());
        response.setBasePrice(roomType.getBasePrice());
        response.setStatus(roomType.getStatus());
        response.setCreatedAt(roomType.getCreatedAt());
        response.setUpdatedAt(roomType.getUpdatedAt());

        return response;
    }

    // ============================================================
    // ROOM REQUEST → ENTITY
    // ============================================================

    public Room toRoomEntity(
            RoomRequest request,
            RoomType roomType) {

        Room room = new Room();

        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(roomType);
        room.setFloor(request.getFloor());
        room.setNotes(request.getNotes());

        if (request.getStatus() != null) {
            room.setStatus(request.getStatus());
        }

        return room;
    }

    // ============================================================
    // ROOM ENTITY → RESPONSE
    // ============================================================

    public RoomResponse toRoomResponse(Room room) {

        RoomResponse response = new RoomResponse();

        response.setId(room.getId());
        response.setRoomNumber(room.getRoomNumber());

        response.setRoomTypeId(
                room.getRoomType().getId()
        );

        response.setRoomTypeName(
                room.getRoomType().getName()
        );

        response.setFloor(room.getFloor());
        response.setStatus(room.getStatus());
        response.setNotes(room.getNotes());
        response.setCreatedAt(room.getCreatedAt());
        response.setUpdatedAt(room.getUpdatedAt());

        return response;
    }
}