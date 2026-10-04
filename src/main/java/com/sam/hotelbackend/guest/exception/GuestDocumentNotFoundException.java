package com.sam.hotelbackend.guest.exception;

public class GuestDocumentNotFoundException extends RuntimeException {

    public GuestDocumentNotFoundException(Long id) {
        super("Guest document not found with id: " + id);
    }
}