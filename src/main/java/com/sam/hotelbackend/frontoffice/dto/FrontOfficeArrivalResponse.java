package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.reservation.entity.ReservationSource;
import com.sam.hotelbackend.reservation.entity.ReservationStatus;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FrontOfficeArrivalResponse {

    private Long reservationId;
    private String reservationNumber;

    private Long guestId;
    private String guestName;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private Integer adults;
    private Integer children;

    private String roomType;

    private ReservationStatus status;
    private ReservationSource source;

    private String specialRequests;
    private String notes;
}