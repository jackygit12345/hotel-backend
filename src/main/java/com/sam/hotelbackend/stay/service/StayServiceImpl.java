package com.sam.hotelbackend.stay.service;

import com.sam.hotelbackend.guest.entity.Guest;
import com.sam.hotelbackend.guest.repository.GuestRepository;
import com.sam.hotelbackend.reservation.entity.Reservation;
import com.sam.hotelbackend.reservation.entity.ReservationStatus;
import com.sam.hotelbackend.reservation.repository.ReservationRepository;
import com.sam.hotelbackend.room.entity.Room;
import com.sam.hotelbackend.room.entity.RoomStatus;
import com.sam.hotelbackend.room.repository.RoomRepository;
import com.sam.hotelbackend.stay.dto.CheckInRequest;
import com.sam.hotelbackend.stay.dto.StayResponse;
import com.sam.hotelbackend.stay.dto.StayExtensionRequest;
import com.sam.hotelbackend.stay.dto.RoomTransferRequest;
import com.sam.hotelbackend.stay.dto.EarlyCheckInRequest;
import com.sam.hotelbackend.stay.dto.LateCheckOutRequest;
import com.sam.hotelbackend.stay.entity.Stay;
import com.sam.hotelbackend.stay.entity.StayStatus;
import com.sam.hotelbackend.stay.exception.InvalidStayOperationException;
import com.sam.hotelbackend.stay.exception.StayNotFoundException;
import com.sam.hotelbackend.stay.mapper.StayMapper;
import com.sam.hotelbackend.stay.repository.StayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StayServiceImpl implements StayService {

    private final StayRepository stayRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final StayMapper stayMapper;

    @Override
    public StayResponse checkIn(CheckInRequest request) {

        Reservation reservation = reservationRepository
                .findById(request.getReservationId())
                .orElseThrow(() ->
                        new InvalidStayOperationException(
                                "Reservation not found with id: "
                                        + request.getReservationId()
                        )
                );

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new InvalidStayOperationException(
                    "Only CONFIRMED reservations can be checked in"
            );
        }

        if (stayRepository.existsByReservationId(
                request.getReservationId())) {

            throw new InvalidStayOperationException(
                    "A stay already exists for this reservation"
            );
        }

        Room room = roomRepository
                .findById(request.getRoomId())
                .orElseThrow(() ->
                        new InvalidStayOperationException(
                                "Room not found with id: "
                                        + request.getRoomId()
                        )
                );

        if (room.getStatus() != RoomStatus.AVAILABLE) {
            throw new InvalidStayOperationException(
                    "Room " + room.getRoomNumber()
                            + " is not available for check-in"
            );
        }

        Guest guest = reservation.getGuest();

        Stay stay = new Stay();

        stay.setStayNumber(generateStayNumber());

        stay.setGuest(guest);
        stay.setReservation(reservation);
        stay.setRoom(room);

        stay.setCheckInDate(
                reservation.getCheckInDate()
        );

        stay.setExpectedCheckOutDate(
                reservation.getCheckOutDate()
        );

        stay.setActualCheckInAt(
                LocalDateTime.now()
        );

        stay.setStatus(StayStatus.IN_HOUSE);

        stay.setNotes(request.getNotes());

        Stay savedStay = stayRepository.save(stay);

        reservation.setStatus(
                ReservationStatus.CHECKED_IN
        );

        room.setStatus(RoomStatus.OCCUPIED);

        return stayMapper.toResponse(savedStay);
    }

    @Override
    @Transactional(readOnly = true)
    public StayResponse getStayById(Long id) {

        Stay stay = stayRepository
                .findById(id)
                .orElseThrow(() ->
                        new StayNotFoundException(id)
                );

        return stayMapper.toResponse(stay);
    }

    @Override
    @Transactional(readOnly = true)
    public StayResponse getStayByReservationId(
            Long reservationId) {

        Stay stay = stayRepository
                .findByReservationId(reservationId)
                .orElseThrow(() ->
                        new InvalidStayOperationException(
                                "Stay not found for reservation id: "
                                        + reservationId
                        )
                );

        return stayMapper.toResponse(stay);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StayResponse> getAllStays() {

        return stayRepository.findAll()
                .stream()
                .map(stayMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StayResponse> getStaysByStatus(
            StayStatus status) {

        return stayRepository.findByStatus(status)
                .stream()
                .map(stayMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StayResponse> getStaysByGuest(
            Long guestId) {

        return stayRepository.findByGuestId(guestId)
                .stream()
                .map(stayMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StayResponse> getStaysByRoom(
            Long roomId) {

        return stayRepository.findByRoomId(roomId)
                .stream()
                .map(stayMapper::toResponse)
                .toList();
    }

    @Override
    public StayResponse checkOut(Long id) {

        Stay stay = stayRepository
                .findById(id)
                .orElseThrow(() ->
                        new StayNotFoundException(id)
                );

        if (stay.getStatus() != StayStatus.IN_HOUSE) {
            throw new InvalidStayOperationException(
                    "Only IN_HOUSE stays can be checked out"
            );
        }

        stay.setActualCheckOutAt(
                LocalDateTime.now()
        );

        stay.setStatus(
                StayStatus.CHECKED_OUT
        );

        Reservation reservation =
                stay.getReservation();

        reservation.setStatus(
                ReservationStatus.CHECKED_OUT
        );

        Room room = stay.getRoom();

        room.setStatus(
                RoomStatus.AVAILABLE
        );

        return stayMapper.toResponse(stay);
    }


    @Override
public StayResponse extendStay(
        Long stayId,
        StayExtensionRequest request) {

    Stay stay = stayRepository
            .findById(stayId)
            .orElseThrow(() ->
                    new StayNotFoundException(stayId)
            );

    if (stay.getStatus() != StayStatus.IN_HOUSE) {
        throw new InvalidStayOperationException(
                "Only IN_HOUSE stays can be extended"
        );
    }

    if (!request.getNewCheckOutDate()
            .isAfter(stay.getExpectedCheckOutDate())) {

        throw new InvalidStayOperationException(
                "New check-out date must be after the current expected check-out date"
        );
    }

    Reservation reservation =
            stay.getReservation();

    if (reservation.getStatus()
            != ReservationStatus.CHECKED_IN) {

        throw new InvalidStayOperationException(
                "Reservation must be CHECKED_IN to extend the stay"
        );
    }

    stay.setExpectedCheckOutDate(
            request.getNewCheckOutDate()
    );

    reservation.setCheckOutDate(
            request.getNewCheckOutDate()
    );

    return stayMapper.toResponse(stay);
}

    @Override
public StayResponse transferRoom(
        Long stayId,
        RoomTransferRequest request) {

    Stay stay = stayRepository
            .findById(stayId)
            .orElseThrow(() ->
                    new StayNotFoundException(stayId)
            );

    if (stay.getStatus() != StayStatus.IN_HOUSE) {
        throw new InvalidStayOperationException(
                "Only IN_HOUSE stays can be transferred to another room"
        );
    }

    Room currentRoom = stay.getRoom();

    if (currentRoom.getId()
            .equals(request.getNewRoomId())) {

        throw new InvalidStayOperationException(
                "The new room must be different from the current room"
        );
    }

    Room newRoom = roomRepository
            .findById(request.getNewRoomId())
            .orElseThrow(() ->
                    new InvalidStayOperationException(
                            "Room not found with id: "
                                    + request.getNewRoomId()
                    )
            );

    if (newRoom.getStatus() != RoomStatus.AVAILABLE) {
        throw new InvalidStayOperationException(
                "Room " + newRoom.getRoomNumber()
                        + " is not available for transfer"
        );
    }

    String reservationRoomType =
            stay.getReservation().getRoomType();

    String newRoomType =
            newRoom.getRoomType().getName();

    if (!reservationRoomType.equalsIgnoreCase(
            newRoomType)) {

        throw new InvalidStayOperationException(
                "Room type mismatch. Reservation requires "
                        + reservationRoomType
                        + " but room "
                        + newRoom.getRoomNumber()
                        + " is "
                        + newRoomType
        );
    }

    currentRoom.setStatus(
            RoomStatus.AVAILABLE
    );

    newRoom.setStatus(
            RoomStatus.OCCUPIED
    );

    stay.setRoom(newRoom);

    return stayMapper.toResponse(stay);
}

@Override
public StayResponse recordEarlyCheckIn(
        Long stayId,
        EarlyCheckInRequest request) {

    Stay stay = stayRepository
            .findById(stayId)
            .orElseThrow(() ->
                    new StayNotFoundException(stayId)
            );

    if (stay.getStatus() != StayStatus.IN_HOUSE) {
        throw new InvalidStayOperationException(
                "Only IN_HOUSE stays can have their check-in time adjusted"
        );
    }

    if (!request.getActualCheckInAt()
            .isBefore(
                    stay.getCheckInDate()
                            .atStartOfDay()
            )) {

        throw new InvalidStayOperationException(
                "Early check-in time must be before the scheduled check-in date"
        );
    }

    if (request.getActualCheckInAt()
            .isAfter(LocalDateTime.now())) {

        throw new InvalidStayOperationException(
                "Actual check-in time cannot be in the future"
        );
    }

    stay.setActualCheckInAt(
            request.getActualCheckInAt()
    );

    return stayMapper.toResponse(stay);
}

@Override
public StayResponse recordLateCheckOut(
        Long stayId,
        LateCheckOutRequest request) {

    Stay stay = stayRepository
            .findById(stayId)
            .orElseThrow(() ->
                    new StayNotFoundException(stayId)
            );

    if (stay.getStatus() != StayStatus.IN_HOUSE) {
        throw new InvalidStayOperationException(
                "Only IN_HOUSE stays can have their check-out time adjusted"
        );
    }

    LocalDateTime scheduledCheckOut =
            stay.getExpectedCheckOutDate()
                    .atStartOfDay();

    if (!request.getActualCheckOutAt()
            .isAfter(scheduledCheckOut)) {

        throw new InvalidStayOperationException(
                "Late check-out time must be after the scheduled check-out date"
        );
    }

    if (request.getActualCheckOutAt()
            .isAfter(LocalDateTime.now())) {

        throw new InvalidStayOperationException(
                "Actual check-out time cannot be in the future"
        );
    }

    stay.setActualCheckOutAt(
            request.getActualCheckOutAt()
    );

    return stayMapper.toResponse(stay);
}


    private String generateStayNumber() {

        String datePart = LocalDateTime.now()
                .format(
                        DateTimeFormatter.ofPattern(
                                "yyyyMMdd"
                        )
                );

        long count = stayRepository.count() + 1;

        return String.format(
                "STAY-%s-%04d",
                datePart,
                count
        );
    }
}