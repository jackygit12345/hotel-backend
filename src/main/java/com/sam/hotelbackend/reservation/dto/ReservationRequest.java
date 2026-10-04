package com.sam.hotelbackend.reservation.dto;

import com.sam.hotelbackend.reservation.entity.ReservationSource;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {

    // ============================================================
    // GUEST
    // ============================================================

    @NotNull
    private Long guestId;

    // ============================================================
    // STAY DATES
    // ============================================================

    @NotNull
    @FutureOrPresent
    private LocalDate checkInDate;

    @NotNull
    @Future
    private LocalDate checkOutDate;

    // ============================================================
    // GUEST COUNTS
    // ============================================================

    @NotNull
    @Min(1)
    private Integer adults;

    @NotNull
    @Min(0)
    private Integer children;

    // ============================================================
    // ROOM INFORMATION
    // ============================================================

    @NotBlank
    @Size(max = 100)
    private String roomType;

    // ============================================================
    // RESERVATION SOURCE
    // ============================================================

    @NotNull
    private ReservationSource source;

    // ============================================================
    // ADDITIONAL INFORMATION
    // ============================================================

    @Size(max = 1000)
    private String specialRequests;

    @Size(max = 2000)
    private String notes;
}