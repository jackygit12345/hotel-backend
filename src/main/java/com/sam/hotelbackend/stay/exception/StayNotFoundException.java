package com.sam.hotelbackend.stay.exception;

public class StayNotFoundException extends RuntimeException {

    public StayNotFoundException(Long id) {
        super("Stay not found with id: " + id);
    }
}