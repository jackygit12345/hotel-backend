package com.sam.hotelbackend.room.service;

import com.sam.hotelbackend.room.dto.RoomTypeRequest;
import com.sam.hotelbackend.room.dto.RoomTypeResponse;
import com.sam.hotelbackend.room.entity.RoomType;
import com.sam.hotelbackend.room.exception.RoomTypeNotFoundException;
import com.sam.hotelbackend.room.mapper.RoomMapper;
import com.sam.hotelbackend.room.repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final RoomMapper roomMapper;

    // ============================================================
    // CREATE
    // ============================================================

    @Override
    public RoomTypeResponse createRoomType(
            RoomTypeRequest request) {

        RoomType roomType =
                roomMapper.toRoomTypeEntity(request);

        RoomType savedRoomType =
                roomTypeRepository.save(roomType);

        return roomMapper.toRoomTypeResponse(savedRoomType);
    }

    // ============================================================
    // GET BY ID
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public RoomTypeResponse getRoomTypeById(Long id) {

        RoomType roomType = findRoomType(id);

        return roomMapper.toRoomTypeResponse(roomType);
    }

    // ============================================================
    // GET ALL
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<RoomTypeResponse> getAllRoomTypes() {

        return roomTypeRepository.findAll()
                .stream()
                .map(roomMapper::toRoomTypeResponse)
                .toList();
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Override
    public RoomTypeResponse updateRoomType(
            Long id,
            RoomTypeRequest request) {

        RoomType roomType = findRoomType(id);

        roomType.setName(request.getName());
        roomType.setDescription(request.getDescription());
        roomType.setMaxAdults(request.getMaxAdults());
        roomType.setMaxChildren(request.getMaxChildren());
        roomType.setBasePrice(request.getBasePrice());

        if (request.getStatus() != null) {
            roomType.setStatus(request.getStatus());
        }

        return roomMapper.toRoomTypeResponse(roomType);
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Override
    public void deleteRoomType(Long id) {

        RoomType roomType = findRoomType(id);

        roomTypeRepository.delete(roomType);
    }

    // ============================================================
    // FIND
    // ============================================================

    private RoomType findRoomType(Long id) {

        return roomTypeRepository.findById(id)
                .orElseThrow(() ->
                        new RoomTypeNotFoundException(id)
                );
    }
}