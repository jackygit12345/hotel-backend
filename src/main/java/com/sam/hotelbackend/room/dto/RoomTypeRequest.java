package com.sam.hotelbackend.room.dto;

import com.sam.hotelbackend.room.entity.RoomTypeStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RoomTypeRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 1000)
    private String description;

    @NotNull
    @Min(1)
    private Integer maxAdults;

    @NotNull
    @Min(0)
    private Integer maxChildren;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal basePrice;

    private RoomTypeStatus status;
}