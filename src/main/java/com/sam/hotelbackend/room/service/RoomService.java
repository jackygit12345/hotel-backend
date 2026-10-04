package com.sam.hotelbackend.room.service;

import com.sam.hotelbackend.room.dto.RoomRequest;
import com.sam.hotelbackend.room.dto.RoomResponse;
import com.sam.hotelbackend.room.entity.RoomStatus;

import java.util.List;

public interface RoomService {

    RoomResponse createRoom(RoomRequest request);

    RoomResponse getRoomById(Long id);

    List<RoomResponse> getAllRooms();

    RoomResponse updateRoom(
            Long id,
            RoomRequest request
    );

    void deleteRoom(Long id);

    List<RoomResponse> getRoomsByRoomType(Long roomTypeId);

    List<RoomResponse> getRoomsByStatus(RoomStatus status);
}