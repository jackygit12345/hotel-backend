package com.sam.hotelbackend.stay.service;

import com.sam.hotelbackend.stay.dto.CheckInRequest;
import com.sam.hotelbackend.stay.dto.StayResponse;
import com.sam.hotelbackend.stay.dto.StayExtensionRequest;
import com.sam.hotelbackend.stay.dto.RoomTransferRequest;
import com.sam.hotelbackend.stay.dto.EarlyCheckInRequest;
import com.sam.hotelbackend.stay.dto.LateCheckOutRequest;
import com.sam.hotelbackend.stay.entity.StayStatus;

import java.util.List;

public interface StayService {

    StayResponse checkIn(CheckInRequest request);

    StayResponse getStayById(Long id);

    StayResponse getStayByReservationId(Long reservationId);

    List<StayResponse> getAllStays();

    List<StayResponse> getStaysByStatus(StayStatus status);

    List<StayResponse> getStaysByGuest(Long guestId);

    List<StayResponse> getStaysByRoom(Long roomId);

    StayResponse checkOut(Long id);
     
    StayResponse extendStay(
            Long stayId,
            StayExtensionRequest request
    );

    StayResponse transferRoom(
        Long stayId,
        RoomTransferRequest request
    );

    StayResponse recordEarlyCheckIn(
        Long stayId,
        EarlyCheckInRequest request
	);

    StayResponse recordLateCheckOut(
        Long stayId,
        LateCheckOutRequest request
    );

}