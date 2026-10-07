package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.guest.dto.GuestResponse;
import com.sam.hotelbackend.reservation.dto.ReservationResponse;
import com.sam.hotelbackend.stay.dto.StayResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WalkInGuestResponse {

    private GuestResponse guest;

    private ReservationResponse reservation;

    private StayResponse stay;
}