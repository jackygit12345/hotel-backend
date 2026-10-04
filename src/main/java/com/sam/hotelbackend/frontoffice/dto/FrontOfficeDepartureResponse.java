package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.reservation.entity.ReservationStatus;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FrontOfficeDepartureResponse {

    private Long stayId;
    private String stayNumber;

    private Long reservationId;
    private String reservationNumber;

    private Long guestId;
    private String guestName;

    private Long roomId;
    private String roomNumber;

    private LocalDate checkInDate;
    private LocalDate expectedCheckOutDate;

    private ReservationStatus reservationStatus;

    private String notes;
}