package com.sam.hotelbackend.billing.exception;

public class InvalidBillingOperationException extends RuntimeException {

    public InvalidBillingOperationException(String message) {
        super(message);
    }
}