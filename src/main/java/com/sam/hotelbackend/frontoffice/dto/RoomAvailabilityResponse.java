package com.sam.hotelbackend.frontoffice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomAvailabilityResponse {

    private Long roomTypeId;
    private String roomTypeName;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private int totalRooms;
    private int reservedRooms;
    private int availableRooms;

    private BigDecimal basePrice;
}