package com.sam.hotelbackend.guest.repository;

import com.sam.hotelbackend.guest.entity.GuestPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestPreferenceRepository extends JpaRepository<GuestPreference, Long> {

    Optional<GuestPreference> findByGuestId(Long guestId);

    boolean existsByGuestId(Long guestId);
}