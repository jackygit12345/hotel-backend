package com.sam.hotelbackend.room.repository;

import com.sam.hotelbackend.room.entity.RoomType;
import com.sam.hotelbackend.room.entity.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomTypeRepository
        extends JpaRepository<RoomType, Long> {

    Optional<RoomType> findByName(String name);

    boolean existsByName(String name);

// ============================================================
// FRONT OFFICE - ROOM AVAILABILITY
// ============================================================


@Query("""
        SELECT COUNT(r)
        FROM Room r
        WHERE r.roomType.id = :roomTypeId
        """)
int findRoomCountByRoomTypeId(
        @Param("roomTypeId") Long roomTypeId
);
}