package com.sam.hotelbackend.guest.repository;

import com.sam.hotelbackend.guest.entity.GuestDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestDocumentRepository extends JpaRepository<GuestDocument, Long> {

List<GuestDocument> findByGuestId(Long guestId);

}