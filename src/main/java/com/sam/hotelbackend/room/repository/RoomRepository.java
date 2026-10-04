package com.sam.hotelbackend.room.repository;

import com.sam.hotelbackend.room.entity.Room;
import com.sam.hotelbackend.room.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository
        extends JpaRepository<Room, Long> {

    Optional<Room> findByRoomNumber(String roomNumber);

    boolean existsByRoomNumber(String roomNumber);

    List<Room> findByRoomTypeId(Long roomTypeId);

    List<Room> findByStatus(RoomStatus status);

    List<Room> findByRoomTypeIdAndStatus(
            Long roomTypeId,
            RoomStatus status
    );
}