package com.sam.hotelbackend.stay.repository;

import com.sam.hotelbackend.stay.entity.Stay;
import com.sam.hotelbackend.stay.entity.StayStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StayRepository extends JpaRepository<Stay, Long> {

    Optional<Stay> findByStayNumber(String stayNumber);

    boolean existsByStayNumber(String stayNumber);

    Optional<Stay> findByReservationId(Long reservationId);

    boolean existsByReservationId(Long reservationId);

    List<Stay> findByStatus(StayStatus status);

    List<Stay> findByGuestId(Long guestId);

    List<Stay> findByRoomId(Long roomId);

    Optional<Stay> findByRoomIdAndStatus(
            Long roomId,
            StayStatus status
    );
}