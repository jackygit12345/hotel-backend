package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.reservation.entity.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ArrivalReportResponse {

    private Long reservationId;

    private String reservationNumber;

    private Long guestId;

    private String guestName;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String roomType;

    private ReservationStatus status;
}