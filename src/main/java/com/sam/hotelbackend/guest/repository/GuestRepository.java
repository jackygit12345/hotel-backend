package com.sam.hotelbackend.guest.repository;

import com.sam.hotelbackend.guest.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sam.hotelbackend.guest.entity.Guest;
import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    // ============================================================
    // FRONT OFFICE - GUEST SEARCH
    // ============================================================
List<Guest> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName,
        String lastName
);

List<Guest> findByEmailContainingIgnoreCase(String email);

List<Guest> findByPhoneContaining(String phone);

}