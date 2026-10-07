package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.frontoffice.dto.WalkInGuestRequest;
import com.sam.hotelbackend.frontoffice.dto.WalkInGuestResponse;
import com.sam.hotelbackend.guest.dto.GuestRequest;
import com.sam.hotelbackend.guest.dto.GuestResponse;
import com.sam.hotelbackend.guest.service.GuestService;
import com.sam.hotelbackend.reservation.dto.ReservationRequest;
import com.sam.hotelbackend.reservation.dto.ReservationResponse;
import com.sam.hotelbackend.reservation.entity.ReservationSource;
import com.sam.hotelbackend.reservation.service.ReservationService;
import com.sam.hotelbackend.room.entity.Room;
import com.sam.hotelbackend.room.repository.RoomRepository;
import com.sam.hotelbackend.stay.dto.CheckInRequest;
import com.sam.hotelbackend.stay.dto.StayResponse;
import com.sam.hotelbackend.stay.service.StayService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FrontOfficeWalkInServiceImpl
        implements FrontOfficeWalkInService {

    private final GuestService guestService;
    private final ReservationService reservationService;
    private final StayService stayService;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public WalkInGuestResponse processWalkIn(
            WalkInGuestRequest request) {

        if (request.getGuestId() != null
                && request.getGuest() != null) {

            throw new IllegalArgumentException(
                    "Provide either guestId or new guest details, not both"
            );
        }

        if (request.getGuestId() == null
                && request.getGuest() == null) {

            throw new IllegalArgumentException(
                    "Either guestId or new guest details are required"
            );
        }

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Room not found: "
                                        + request.getRoomId()
                        )
                );

        if (room.getStatus().name().equals("AVAILABLE") == false) {

            throw new IllegalArgumentException(
                    "Selected room is not available"
            );
        }

        GuestResponse guest;

        if (request.getGuestId() != null) {

            guest = guestService.getGuestById(
                    request.getGuestId()
            );

        } else {

            GuestRequest guestRequest =
                    request.getGuest();

            guest = guestService.createGuest(
                    guestRequest
            );
        }

        LocalDate checkInDate = LocalDate.now();

        ReservationRequest reservationRequest =
                new ReservationRequest();

        reservationRequest.setGuestId(
                guest.getId()
        );

        reservationRequest.setCheckInDate(
                checkInDate
        );

        reservationRequest.setCheckOutDate(
                request.getCheckOutDate()
        );

        reservationRequest.setAdults(
                request.getAdults()
        );

        reservationRequest.setChildren(
                request.getChildren()
        );

        reservationRequest.setRoomType(
                room.getRoomType().getName()
        );

        reservationRequest.setSource(
                ReservationSource.WALK_IN
        );

        reservationRequest.setSpecialRequests(
                request.getSpecialRequests()
        );

        reservationRequest.setNotes(
                request.getNotes()
        );

        ReservationResponse reservation =
                reservationService.createReservation(
                        reservationRequest
                );

        reservationService.confirmReservation(
                reservation.getId()
        );

        CheckInRequest checkInRequest =
                new CheckInRequest();

        checkInRequest.setReservationId(
                reservation.getId()
        );

        checkInRequest.setRoomId(
                request.getRoomId()
        );

        checkInRequest.setNotes(
                request.getNotes()
        );

        StayResponse stay =
                stayService.checkIn(
                        checkInRequest
                );

        return new WalkInGuestResponse(
                guest,
                reservationService.getReservationById(
                        reservation.getId()
                ),
                stay
        );
    }
}