package com.sam.hotelbackend.reservation.dto;

import com.sam.hotelbackend.reservation.entity.ReservationSource;
import com.sam.hotelbackend.reservation.entity.ReservationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationResponse {

    // ============================================================
    // RESERVATION
    // ============================================================

    private Long id;

    private String reservationNumber;

    // ============================================================
    // GUEST
    // ============================================================

    private Long guestId;

    private String guestName;

    // ============================================================
    // STAY DATES
    // ============================================================

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    // ============================================================
    // GUEST COUNTS
    // ============================================================

    private Integer adults;

    private Integer children;

    // ============================================================
    // ROOM INFORMATION
    // ============================================================

    private String roomType;

    // ============================================================
    // RESERVATION STATUS
    // ============================================================

    private ReservationStatus status;

    // ============================================================
    // RESERVATION SOURCE
    // ============================================================

    private ReservationSource source;

    // ============================================================
    // ADDITIONAL INFORMATION
    // ============================================================

    private String specialRequests;

    private String notes;

    // ============================================================
    // AUDIT FIELDS
    // ============================================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}