package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.stay.entity.StayStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FrontOfficeInHouseResponse {

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

    private LocalDateTime actualCheckInAt;

    private StayStatus status;

    private String notes;
}