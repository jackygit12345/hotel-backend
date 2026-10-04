package com.sam.hotelbackend.guest.exception;

public class GuestNotFoundException extends RuntimeException {

    public GuestNotFoundException(Long id) {
        super("Guest not found with id: " + id);
    }
}