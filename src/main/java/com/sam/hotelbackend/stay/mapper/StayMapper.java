package com.sam.hotelbackend.stay.mapper;

import com.sam.hotelbackend.stay.dto.StayResponse;
import com.sam.hotelbackend.stay.entity.Stay;
import org.springframework.stereotype.Component;

@Component
public class StayMapper {

    public StayResponse toResponse(Stay stay) {

        StayResponse response = new StayResponse();

        response.setId(stay.getId());
        response.setStayNumber(stay.getStayNumber());

        response.setGuestId(stay.getGuest().getId());
        response.setGuestName(
                stay.getGuest().getFirstName()
                        + " "
                        + stay.getGuest().getLastName()
        );

        response.setReservationId(stay.getReservation().getId());
        response.setReservationNumber(
                stay.getReservation().getReservationNumber()
        );

        response.setRoomId(stay.getRoom().getId());
        response.setRoomNumber(stay.getRoom().getRoomNumber());

        response.setCheckInDate(stay.getCheckInDate());
        response.setExpectedCheckOutDate(
                stay.getExpectedCheckOutDate()
        );

        response.setActualCheckInAt(stay.getActualCheckInAt());
        response.setActualCheckOutAt(stay.getActualCheckOutAt());

        response.setStatus(stay.getStatus());

        response.setNotes(stay.getNotes());

        response.setCreatedAt(stay.getCreatedAt());
        response.setUpdatedAt(stay.getUpdatedAt());

        return response;
    }
}