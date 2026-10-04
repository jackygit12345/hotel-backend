package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.frontoffice.dto.FrontOfficeArrivalResponse;
import com.sam.hotelbackend.frontoffice.dto.FrontOfficeDepartureResponse;
import com.sam.hotelbackend.frontoffice.dto.FrontOfficeInHouseResponse;
import com.sam.hotelbackend.frontoffice.dto.FrontOfficeSummaryResponse;
import com.sam.hotelbackend.frontoffice.dto.RoomStatusResponse;
import com.sam.hotelbackend.reservation.entity.Reservation;
import com.sam.hotelbackend.reservation.repository.ReservationRepository;
import com.sam.hotelbackend.room.entity.Room;
import com.sam.hotelbackend.room.entity.RoomStatus;
import com.sam.hotelbackend.room.repository.RoomRepository;
import com.sam.hotelbackend.stay.entity.Stay;
import com.sam.hotelbackend.stay.repository.StayRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FrontOfficeServiceImpl implements FrontOfficeService {

    private final ReservationRepository reservationRepository;
    private final StayRepository stayRepository;
    private final RoomRepository roomRepository;

    // ============================================================
    // FRONT OFFICE SUMMARY
    // ============================================================

    @Override
    public FrontOfficeSummaryResponse getSummary() {

        LocalDate today = LocalDate.now();

        FrontOfficeSummaryResponse response =
                new FrontOfficeSummaryResponse();

        response.setTodaysArrivals(
                reservationRepository.findByCheckInDate(today).size()
        );

        response.setTodaysDepartures(
                reservationRepository.findByCheckOutDate(today).size()
        );

        response.setInHouseGuests(
                stayRepository.findByStatus(
                        com.sam.hotelbackend.stay.entity.StayStatus.IN_HOUSE
                ).size()
        );

        List<Room> allRooms = roomRepository.findAll();

        response.setTotalRooms(allRooms.size());

        response.setAvailableRooms(
                countRoomsByStatus(allRooms, RoomStatus.AVAILABLE)
        );

        response.setReservedRooms(
                countRoomsByStatus(allRooms, RoomStatus.RESERVED)
        );

        response.setOccupiedRooms(
                countRoomsByStatus(allRooms, RoomStatus.OCCUPIED)
        );

        response.setCleaningRooms(
                countRoomsByStatus(allRooms, RoomStatus.CLEANING)
        );

        response.setMaintenanceRooms(
                countRoomsByStatus(allRooms, RoomStatus.MAINTENANCE)
        );

        response.setOutOfOrderRooms(
                countRoomsByStatus(allRooms, RoomStatus.OUT_OF_ORDER)
        );

        return response;
    }

    // ============================================================
    // TODAY'S ARRIVALS
    // ============================================================

    @Override
    public List<FrontOfficeArrivalResponse> getTodaysArrivals() {

        return reservationRepository
                .findByCheckInDate(LocalDate.now())
                .stream()
                .map(this::mapArrival)
                .toList();
    }

    // ============================================================
    // TODAY'S DEPARTURES
    // ============================================================

    @Override
    public List<FrontOfficeDepartureResponse> getTodaysDepartures() {

        return stayRepository
                .findByStatus(
                        com.sam.hotelbackend.stay.entity.StayStatus.IN_HOUSE
                )
                .stream()
                .filter(stay ->
                        stay.getExpectedCheckOutDate()
                                .equals(LocalDate.now())
                )
                .map(this::mapDeparture)
                .toList();
    }

    // ============================================================
    // CURRENT IN-HOUSE GUESTS
    // ============================================================

    @Override
    public List<FrontOfficeInHouseResponse> getInHouseGuests() {

        return stayRepository
                .findByStatus(
                        com.sam.hotelbackend.stay.entity.StayStatus.IN_HOUSE
                )
                .stream()
                .map(this::mapInHouse)
                .toList();
    }

    // ============================================================
    // AVAILABLE ROOMS
    // ============================================================

    @Override
    public List<RoomStatusResponse> getAvailableRooms() {

        return roomRepository
                .findByStatus(RoomStatus.AVAILABLE)
                .stream()
                .map(this::mapRoom)
                .toList();
    }

    // ============================================================
    // OCCUPIED ROOMS
    // ============================================================

    @Override
    public List<RoomStatusResponse> getOccupiedRooms() {

        return roomRepository
                .findByStatus(RoomStatus.OCCUPIED)
                .stream()
                .map(this::mapRoom)
                .toList();
    }

    // ============================================================
    // ALL ROOM STATUS
    // ============================================================

    @Override
    public List<RoomStatusResponse> getRoomStatus() {

        return roomRepository
                .findAll()
                .stream()
                .map(this::mapRoom)
                .toList();
    }

    // ============================================================
    // MAPPERS
    // ============================================================

    private FrontOfficeArrivalResponse mapArrival(
            Reservation reservation) {

        FrontOfficeArrivalResponse response =
                new FrontOfficeArrivalResponse();

        response.setReservationId(reservation.getId());
        response.setReservationNumber(
                reservation.getReservationNumber()
        );

        response.setGuestId(
                reservation.getGuest().getId()
        );

        response.setGuestName(
                getGuestName(reservation)
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
        response.setNotes(reservation.getNotes());

        return response;
    }

    private FrontOfficeDepartureResponse mapDeparture(
            Stay stay) {

        FrontOfficeDepartureResponse response =
                new FrontOfficeDepartureResponse();

        response.setStayId(stay.getId());
        response.setStayNumber(stay.getStayNumber());

        response.setReservationId(
                stay.getReservation().getId()
        );

        response.setReservationNumber(
                stay.getReservation().getReservationNumber()
        );

        response.setGuestId(
                stay.getGuest().getId()
        );

        response.setGuestName(
                getGuestName(stay)
        );

        response.setRoomId(
                stay.getRoom().getId()
        );

        response.setRoomNumber(
                stay.getRoom().getRoomNumber()
        );

        response.setCheckInDate(
                stay.getCheckInDate()
        );

        response.setExpectedCheckOutDate(
                stay.getExpectedCheckOutDate()
        );

        response.setReservationStatus(
                stay.getReservation().getStatus()
        );

        response.setNotes(stay.getNotes());

        return response;
    }

    private FrontOfficeInHouseResponse mapInHouse(
            Stay stay) {

        FrontOfficeInHouseResponse response =
                new FrontOfficeInHouseResponse();

        response.setStayId(stay.getId());
        response.setStayNumber(stay.getStayNumber());

        response.setGuestId(
                stay.getGuest().getId()
        );

        response.setGuestName(
                getGuestName(stay)
        );

        response.setReservationId(
                stay.getReservation().getId()
        );

        response.setReservationNumber(
                stay.getReservation().getReservationNumber()
        );

        response.setRoomId(
                stay.getRoom().getId()
        );

        response.setRoomNumber(
                stay.getRoom().getRoomNumber()
        );

        response.setCheckInDate(
                stay.getCheckInDate()
        );

        response.setExpectedCheckOutDate(
                stay.getExpectedCheckOutDate()
        );

        response.setActualCheckInAt(
                stay.getActualCheckInAt()
        );

        response.setStatus(stay.getStatus());
        response.setNotes(stay.getNotes());

        return response;
    }

    private RoomStatusResponse mapRoom(Room room) {

        RoomStatusResponse response =
                new RoomStatusResponse();

        response.setRoomId(room.getId());
        response.setRoomNumber(room.getRoomNumber());

        response.setRoomTypeId(
                room.getRoomType().getId()
        );

        response.setRoomTypeName(
                room.getRoomType().getName()
        );

        response.setFloor(room.getFloor());
        response.setStatus(room.getStatus());
        response.setNotes(room.getNotes());

        return response;
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    private long countRoomsByStatus(
            List<Room> rooms,
            RoomStatus status) {

        return rooms.stream()
                .filter(room -> room.getStatus() == status)
                .count();
    }

    private String getGuestName(Reservation reservation) {

        return reservation.getGuest().getFirstName()
                + " "
                + reservation.getGuest().getLastName();
    }

    private String getGuestName(Stay stay) {

        return stay.getGuest().getFirstName()
                + " "
                + stay.getGuest().getLastName();
    }
}