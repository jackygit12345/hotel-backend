package com.sam.hotelbackend.stay.dto;

import com.sam.hotelbackend.stay.entity.StayStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StayResponse {

    private Long id;

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
    private LocalDateTime actualCheckOutAt;

    private StayStatus status;

    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}