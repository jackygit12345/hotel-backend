package com.sam.hotelbackend.frontoffice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OccupancyReportResponse {

    private int totalRooms;

    private int occupiedRooms;

    private int reservedRooms;

    private int availableRooms;

    private int cleaningRooms;

    private int maintenanceRooms;

    private int outOfOrderRooms;

    private double occupancyPercentage;
}