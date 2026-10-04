package com.sam.hotelbackend.stay.exception;

public class InvalidStayOperationException extends RuntimeException {

    public InvalidStayOperationException(String message) {
        super(message);
    }
}