package com.sam.hotelbackend.stay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LateCheckOutRequest {

    @NotNull
    private LocalDateTime actualCheckOutAt;
}