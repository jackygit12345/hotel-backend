package com.sam.hotelbackend.stay.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckInRequest {

    @NotNull
    private Long reservationId;

    @NotNull
    private Long roomId;

    @Size(max = 1000)
    private String notes;
}