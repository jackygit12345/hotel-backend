package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.guest.dto.GuestRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class WalkInGuestRequest {

    // Existing guest OR new guest details
    private Long guestId;

    @Valid
    private GuestRequest guest;

    @NotNull
    @Future
    private LocalDate checkOutDate;

    @NotNull
    @Min(1)
    private Integer adults;

    @NotNull
    @Min(0)
    private Integer children;

    @NotNull
    private Long roomId;

    private String specialRequests;

    private String notes;
}