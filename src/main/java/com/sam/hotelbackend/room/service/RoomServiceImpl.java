package com.sam.hotelbackend.room.service;

import com.sam.hotelbackend.room.dto.RoomRequest;
import com.sam.hotelbackend.room.dto.RoomResponse;
import com.sam.hotelbackend.room.entity.Room;
import com.sam.hotelbackend.room.entity.RoomStatus;
import com.sam.hotelbackend.room.entity.RoomType;
import com.sam.hotelbackend.room.exception.RoomNotFoundException;
import com.sam.hotelbackend.room.exception.RoomTypeNotFoundException;
import com.sam.hotelbackend.room.mapper.RoomMapper;
import com.sam.hotelbackend.room.repository.RoomRepository;
import com.sam.hotelbackend.room.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomMapper roomMapper;

    // ============================================================
    // CREATE ROOM
    // ============================================================

    @Override
    public RoomResponse createRoom(RoomRequest request) {

        RoomType roomType = findRoomType(
                request.getRoomTypeId()
        );

        Room room =
                roomMapper.toRoomEntity(request, roomType);

        Room savedRoom =
                roomRepository.save(room);

        return roomMapper.toRoomResponse(savedRoom);
    }

    // ============================================================
    // GET BY ID
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public RoomResponse getRoomById(Long id) {

        Room room = findRoom(id);

        return roomMapper.toRoomResponse(room);
    }

    // ============================================================
    // GET ALL
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getAllRooms() {

        return roomRepository.findAll()
                .stream()
                .map(roomMapper::toRoomResponse)
                .toList();
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Override
    public RoomResponse updateRoom(
            Long id,
            RoomRequest request) {

        Room room = findRoom(id);

        RoomType roomType = findRoomType(
                request.getRoomTypeId()
        );

        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(roomType);
        room.setFloor(request.getFloor());
        room.setNotes(request.getNotes());

        if (request.getStatus() != null) {
            room.setStatus(request.getStatus());
        }

        return roomMapper.toRoomResponse(room);
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Override
    public void deleteRoom(Long id) {

        Room room = findRoom(id);

        roomRepository.delete(room);
    }

    // ============================================================
    // GET BY ROOM TYPE
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getRoomsByRoomType(
            Long roomTypeId) {

        findRoomType(roomTypeId);

        return roomRepository
                .findByRoomTypeId(roomTypeId)
                .stream()
                .map(roomMapper::toRoomResponse)
                .toList();
    }

    // ============================================================
    // GET BY STATUS
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getRoomsByStatus(
            RoomStatus status) {

        return roomRepository
                .findByStatus(status)
                .stream()
                .map(roomMapper::toRoomResponse)
                .toList();
    }

    // ============================================================
    // FIND ROOM
    // ============================================================

    private Room findRoom(Long id) {

        return roomRepository.findById(id)
                .orElseThrow(() ->
                        new RoomNotFoundException(id)
                );
    }

    // ============================================================
    // FIND ROOM TYPE
    // ============================================================

    private RoomType findRoomType(Long id) {

        return roomTypeRepository.findById(id)
                .orElseThrow(() ->
                        new RoomTypeNotFoundException(id)
                );
    }
}