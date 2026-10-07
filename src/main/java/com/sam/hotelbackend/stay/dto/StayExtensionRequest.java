package com.sam.hotelbackend.stay.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class StayExtensionRequest {

    @NotNull
    @Future
    private LocalDate newCheckOutDate;
}