package com.sam.hotelbackend.guest.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sam.hotelbackend.room.exception.RoomNotFoundException;
import com.sam.hotelbackend.room.exception.RoomTypeNotFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sam.hotelbackend.stay.exception.InvalidStayOperationException;
import com.sam.hotelbackend.stay.exception.StayNotFoundException;

import com.sam.hotelbackend.billing.exception.InvalidBillingOperationException;
import com.sam.hotelbackend.billing.exception.InvoiceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ============================================================
    // GUEST NOT FOUND
    // ============================================================

    @ExceptionHandler(GuestNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleGuestNotFound(
            GuestNotFoundException exception) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    // ============================================================
    // GUEST DOCUMENT NOT FOUND
    // ============================================================

    @ExceptionHandler(GuestDocumentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleGuestDocumentNotFound(
            GuestDocumentNotFoundException exception) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    // ============================================================
    // GUEST PREFERENCE NOT FOUND
    // ============================================================

    @ExceptionHandler(GuestPreferenceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleGuestPreferenceNotFound(
            GuestPreferenceNotFoundException exception) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }


    // ============================================================
    // ROOM NOT FOUND
    // ============================================================

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRoomNotFound(
            RoomNotFoundException exception) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    // ============================================================
    // ROOM TYPE NOT FOUND
    // ============================================================

    @ExceptionHandler(RoomTypeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRoomTypeNotFound(
            RoomTypeNotFoundException exception) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }


    // ============================================================
    // VALIDATION ERRORS
    // ============================================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, Object> errors = new LinkedHashMap<>();

        errors.put("timestamp", LocalDateTime.now());
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());

        Map<String, String> fieldErrors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        errors.put("validationErrors", fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    // ============================================================
    // DATABASE INTEGRITY VIOLATION
    // ============================================================

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException exception) {

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "The requested operation conflicts with existing related data."
        );
    }

    // ============================================================
    // STAY NOT FOUND
    // ============================================================

    @ExceptionHandler(StayNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStayNotFound(
            StayNotFoundException exception) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    // ============================================================
    // INVALID STAY OPERATION
    // ============================================================

    @ExceptionHandler(InvalidStayOperationException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidStayOperation(
            InvalidStayOperationException exception) {

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }


    // ============================================================
    // INVOICE NOT FOUND
    // ============================================================

    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleInvoiceNotFound(
            InvoiceNotFoundException exception) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
    }

    // ============================================================
    // INVALID BILLING OPERATION
    // ============================================================

    @ExceptionHandler(InvalidBillingOperationException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidBillingOperation(
            InvalidBillingOperationException exception) {

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
    }


    // ============================================================
    // COMMON ERROR RESPONSE
    // ============================================================

    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            HttpStatus status,
            String message) {

        Map<String, Object> error = new LinkedHashMap<>();

        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);

        return ResponseEntity
                .status(status)
                .body(error);
    }

// ============================================================
// INVALID FRONT OFFICE SEARCH / AVAILABILITY OPERATION
// ============================================================

@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
        IllegalArgumentException exception) {

    return buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            exception.getMessage()
    );
}
}