package com.sam.hotelbackend.frontoffice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FrontOfficeSummaryResponse {

    private long todaysArrivals;
    private long todaysDepartures;
    private long inHouseGuests;

    private long availableRooms;
    private long reservedRooms;
    private long occupiedRooms;
    private long cleaningRooms;
    private long maintenanceRooms;
    private long outOfOrderRooms;

    private long totalRooms;
}