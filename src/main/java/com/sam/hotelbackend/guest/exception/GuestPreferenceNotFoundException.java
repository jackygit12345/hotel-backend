package com.sam.hotelbackend.guest.exception;

public class GuestPreferenceNotFoundException extends RuntimeException {

    public GuestPreferenceNotFoundException(Long guestId) {
        super("Guest preferences not found for guest ID: " + guestId);
    }
}