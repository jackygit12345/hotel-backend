package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.stay.entity.StayStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class InHouseReportResponse {

    private Long stayId;

    private String stayNumber;

    private Long guestId;

    private String guestName;

    private Long roomId;

    private String roomNumber;

    private String roomType;

    private LocalDate checkInDate;

    private LocalDate expectedCheckOutDate;

    private LocalDateTime actualCheckInAt;

    private StayStatus status;
}