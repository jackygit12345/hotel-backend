package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.frontoffice.dto.GuestSearchResponse;
import com.sam.hotelbackend.frontoffice.dto.ReservationSearchResponse;
import com.sam.hotelbackend.frontoffice.dto.RoomAvailabilityResponse;
import com.sam.hotelbackend.guest.entity.Guest;
import com.sam.hotelbackend.guest.repository.GuestRepository;
import com.sam.hotelbackend.reservation.entity.Reservation;
import com.sam.hotelbackend.reservation.entity.ReservationStatus;
import com.sam.hotelbackend.reservation.repository.ReservationRepository;
import com.sam.hotelbackend.room.entity.RoomType;
import com.sam.hotelbackend.room.entity.RoomTypeStatus;
import com.sam.hotelbackend.room.repository.RoomTypeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FrontOfficeSearchServiceImpl
        implements FrontOfficeSearchService {

    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final RoomTypeRepository roomTypeRepository;

    // ============================================================
    // GUEST SEARCH
    // ============================================================

    @Override
    public List<GuestSearchResponse> searchGuests(
            String keyword) {

        String searchKeyword = keyword == null
                ? ""
                : keyword.trim();

        if (searchKeyword.isBlank()) {
            return guestRepository.findAll()
                    .stream()
                    .map(this::mapGuest)
                    .toList();
        }

        List<Guest> guests =
                guestRepository
                        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                                searchKeyword,
                                searchKeyword
                        );

        return guests.stream()
                .map(this::mapGuest)
                .toList();
    }

    // ============================================================
    // RESERVATION SEARCH
    // ============================================================

    @Override
    public List<ReservationSearchResponse> searchReservations(
            String reservationNumber) {

        String searchNumber = reservationNumber == null
                ? ""
                : reservationNumber.trim();

        if (searchNumber.isBlank()) {
            return reservationRepository.findAll()
                    .stream()
                    .map(this::mapReservation)
                    .toList();
        }

        return reservationRepository
                .findByReservationNumberContainingIgnoreCase(
                        searchNumber
                )
                .stream()
                .map(this::mapReservation)
                .toList();
    }

    // ============================================================
    // RESERVATIONS BY GUEST
    // ============================================================

    @Override
    public List<ReservationSearchResponse> getReservationsByGuest(
            Long guestId) {

        return reservationRepository
                .findByGuestId(guestId)
                .stream()
                .map(this::mapReservation)
                .toList();
    }

    // ============================================================
    // ROOM AVAILABILITY
    // ============================================================

    @Override
    public List<RoomAvailabilityResponse> getRoomAvailability(
            LocalDate checkInDate,
            LocalDate checkOutDate) {

        if (!checkInDate.isBefore(checkOutDate)) {
            throw new IllegalArgumentException(
                    "Check-in date must be before check-out date"
            );
        }

        return roomTypeRepository
                .findAll()
                .stream()
                .filter(roomType ->
                        roomType.getStatus() == RoomTypeStatus.ACTIVE
                )
                .map(roomType ->
                        calculateAvailability(
                                roomType,
                                checkInDate,
                                checkOutDate
                        )
                )
                .toList();
    }

    // ============================================================
    // CALCULATE ROOM TYPE AVAILABILITY
    // ============================================================

    private RoomAvailabilityResponse calculateAvailability(
            RoomType roomType,
            LocalDate checkInDate,
            LocalDate checkOutDate) {

        int totalRooms =
                roomTypeRepository
                        .findRoomCountByRoomTypeId(
                                roomType.getId()
                        );

        List<Reservation> overlappingReservations =
                reservationRepository
                        .findByRoomTypeAndCheckInDateLessThanAndCheckOutDateGreaterThan(
                                roomType.getName(),
                                checkOutDate,
                                checkInDate
                        );

        int reservedRooms =
                (int) overlappingReservations
                        .stream()
                        .filter(this::countsAsReserved)
                        .count();

        int availableRooms =
                Math.max(0, totalRooms - reservedRooms);

        RoomAvailabilityResponse response =
                new RoomAvailabilityResponse();

        response.setRoomTypeId(roomType.getId());
        response.setRoomTypeName(roomType.getName());

        response.setCheckInDate(checkInDate);
        response.setCheckOutDate(checkOutDate);

        response.setTotalRooms(totalRooms);
        response.setReservedRooms(reservedRooms);
        response.setAvailableRooms(availableRooms);

        response.setBasePrice(roomType.getBasePrice());

        return response;
    }

    // ============================================================
    // RESERVATION STATUS
    // ============================================================

    private boolean countsAsReserved(
            Reservation reservation) {

        return reservation.getStatus() == ReservationStatus.PENDING
                || reservation.getStatus() == ReservationStatus.CONFIRMED
                || reservation.getStatus() == ReservationStatus.CHECKED_IN;
    }

    // ============================================================
    // GUEST MAPPER
    // ============================================================

    private GuestSearchResponse mapGuest(Guest guest) {

        GuestSearchResponse response =
                new GuestSearchResponse();

        response.setId(guest.getId());
        response.setFirstName(guest.getFirstName());
        response.setLastName(guest.getLastName());
        response.setEmail(guest.getEmail());
        response.setPhone(guest.getPhone());
        response.setGuestType(guest.getGuestType());
        response.setStatus(guest.getStatus());

        return response;
    }

    // ============================================================
    // RESERVATION MAPPER
    // ============================================================

    private ReservationSearchResponse mapReservation(
            Reservation reservation) {

        ReservationSearchResponse response =
                new ReservationSearchResponse();

        response.setId(reservation.getId());
        response.setReservationNumber(
                reservation.getReservationNumber()
        );

        response.setGuestId(
                reservation.getGuest().getId()
        );

        response.setGuestName(
                reservation.getGuest().getFirstName()
                        + " "
                        + reservation.getGuest().getLastName()
        );

        response.setCheckInDate(
                reservation.getCheckInDate()
        );

        response.setCheckOutDate(
                reservation.getCheckOutDate()
        );

        response.setAdults(reservation.getAdults());
        response.setChildren(reservation.getChildren());
        response.setRoomType(reservation.getRoomType());

        response.setStatus(reservation.getStatus());
        response.setSource(reservation.getSource());

        response.setSpecialRequests(
                reservation.getSpecialRequests()
        );

        return response;
    }
}