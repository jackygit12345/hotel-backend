package com.sam.hotelbackend.room.exception;

public class RoomTypeNotFoundException extends RuntimeException {

    public RoomTypeNotFoundException(Long id) {
        super("Room type not found with id: " + id);
    }
}