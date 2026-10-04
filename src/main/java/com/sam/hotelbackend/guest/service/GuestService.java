package com.sam.hotelbackend.guest.service;

import com.sam.hotelbackend.guest.dto.GuestDocumentRequest;
import com.sam.hotelbackend.guest.dto.GuestDocumentResponse;
import com.sam.hotelbackend.guest.dto.GuestPreferenceRequest;
import com.sam.hotelbackend.guest.dto.GuestPreferenceResponse;
import com.sam.hotelbackend.guest.dto.GuestRequest;
import com.sam.hotelbackend.guest.dto.GuestResponse;

import java.util.List;

public interface GuestService {

    GuestResponse createGuest(GuestRequest request);

    GuestResponse getGuestById(Long id);

    List<GuestResponse> getAllGuests();

    GuestResponse updateGuest(Long id, GuestRequest request);

    void deleteGuest(Long id);

    GuestDocumentResponse addDocument(
            Long guestId,
            GuestDocumentRequest request
    );

    List<GuestDocumentResponse> getGuestDocuments(Long guestId);

    void deleteDocument(Long guestId, Long documentId);

    GuestPreferenceResponse savePreferences(
            Long guestId,
            GuestPreferenceRequest request
    );

    GuestPreferenceResponse getPreferences(Long guestId);
}

