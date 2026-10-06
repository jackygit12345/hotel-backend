package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.stay.entity.StayStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DepartureReportResponse {

    private Long stayId;

    private String stayNumber;

    private Long guestId;

    private String guestName;

    private Long reservationId;

    private String reservationNumber;

    private Long roomId;

    private String roomNumber;

    private LocalDate checkInDate;

    private LocalDate expectedCheckOutDate;

    private StayStatus status;
}