package com.sam.hotelbackend.room.service;

import com.sam.hotelbackend.room.dto.RoomTypeRequest;
import com.sam.hotelbackend.room.dto.RoomTypeResponse;

import java.util.List;

public interface RoomTypeService {

    RoomTypeResponse createRoomType(RoomTypeRequest request);

    RoomTypeResponse getRoomTypeById(Long id);

    List<RoomTypeResponse> getAllRoomTypes();

    RoomTypeResponse updateRoomType(
            Long id,
            RoomTypeRequest request
    );

    void deleteRoomType(Long id);
}