package com.sam.hotelbackend.guest.service;

import com.sam.hotelbackend.guest.dto.GuestDocumentRequest;
import com.sam.hotelbackend.guest.dto.GuestDocumentResponse;
import com.sam.hotelbackend.guest.dto.GuestPreferenceRequest;
import com.sam.hotelbackend.guest.dto.GuestPreferenceResponse;
import com.sam.hotelbackend.guest.dto.GuestRequest;
import com.sam.hotelbackend.guest.dto.GuestResponse;
import com.sam.hotelbackend.guest.entity.Guest;
import com.sam.hotelbackend.guest.entity.GuestDocument;
import com.sam.hotelbackend.guest.entity.GuestPreference;
import com.sam.hotelbackend.guest.exception.GuestDocumentNotFoundException;
import com.sam.hotelbackend.guest.exception.GuestNotFoundException;
import com.sam.hotelbackend.guest.exception.GuestPreferenceNotFoundException;
import com.sam.hotelbackend.guest.mapper.GuestMapper;
import com.sam.hotelbackend.guest.repository.GuestDocumentRepository;
import com.sam.hotelbackend.guest.repository.GuestPreferenceRepository;
import com.sam.hotelbackend.guest.repository.GuestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final GuestDocumentRepository guestDocumentRepository;
    private final GuestPreferenceRepository guestPreferenceRepository;
    private final GuestMapper guestMapper;

    public GuestServiceImpl(
            GuestRepository guestRepository,
            GuestDocumentRepository guestDocumentRepository,
            GuestPreferenceRepository guestPreferenceRepository,
            GuestMapper guestMapper
    ) {
        this.guestRepository = guestRepository;
        this.guestDocumentRepository = guestDocumentRepository;
        this.guestPreferenceRepository = guestPreferenceRepository;
        this.guestMapper = guestMapper;
    }

    @Override
    public GuestResponse createGuest(GuestRequest request) {

        Guest guest = guestMapper.toEntity(request);
        Guest savedGuest = guestRepository.save(guest);

        return guestMapper.toResponse(savedGuest);
    }

    @Override
    @Transactional(readOnly = true)
    public GuestResponse getGuestById(Long id) {

        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));

        return guestMapper.toResponse(guest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestResponse> getAllGuests() {

        return guestRepository.findAll()
                .stream()
                .map(guestMapper::toResponse)
                .toList();
    }

    @Override
    public GuestResponse updateGuest(Long id, GuestRequest request) {

        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));

        guest.setGuestType(request.getGuestType());
        guest.setFirstName(request.getFirstName());
        guest.setLastName(request.getLastName());
        guest.setEmail(request.getEmail());
        guest.setPhone(request.getPhone());
        guest.setNationality(request.getNationality());
        guest.setPreferences(request.getPreferences());
        guest.setNotes(request.getNotes());

        Guest updatedGuest = guestRepository.save(guest);

        return guestMapper.toResponse(updatedGuest);
    }

    @Override
    public void deleteGuest(Long id) {

        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestNotFoundException(id));

        guestRepository.delete(guest);
    }

    @Override
    public GuestDocumentResponse addDocument(
            Long guestId,
            GuestDocumentRequest request
    ) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(guestId));

        GuestDocument document = guestMapper.toDocumentEntity(request);
        document.setGuest(guest);

        GuestDocument savedDocument =
                guestDocumentRepository.save(document);

        return guestMapper.toDocumentResponse(savedDocument);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDocumentResponse> getGuestDocuments(Long guestId) {

        if (!guestRepository.existsById(guestId)) {
        throw new GuestNotFoundException(guestId);
    	}

    	return guestDocumentRepository.findByGuestId(guestId)
            .stream()
            .map(guestMapper::toDocumentResponse)
            .toList();
    }

    @Override
    public void deleteDocument(Long guestId, Long documentId) {

        GuestDocument document = guestDocumentRepository.findById(documentId)
                .orElseThrow(() ->
                        new GuestDocumentNotFoundException(documentId));

        if (!document.getGuest().getId().equals(guestId)) {
            throw new GuestDocumentNotFoundException(documentId);
        }

        guestDocumentRepository.delete(document);
    }

    @Override
    public GuestPreferenceResponse savePreferences(
            Long guestId,
            GuestPreferenceRequest request
    ) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(guestId));

        GuestPreference preference =
                guestPreferenceRepository.findByGuestId(guestId)
                        .orElseGet(() -> {
                            GuestPreference newPreference =
                                    guestMapper.toPreferenceEntity(request);

                            newPreference.setGuest(guest);

                            return newPreference;
                        });

        preference.setRoomPreference(request.getRoomPreference());
        preference.setBedPreference(request.getBedPreference());
        preference.setSmokingPreference(request.getSmokingPreference());
        preference.setDietaryPreference(request.getDietaryPreference());
        preference.setSpecialRequirements(
                request.getSpecialRequirements()
        );

        GuestPreference savedPreference =
                guestPreferenceRepository.save(preference);

        return guestMapper.toPreferenceResponse(savedPreference);
    }

    @Override
    @Transactional(readOnly = true)
    public GuestPreferenceResponse getPreferences(Long guestId) {

        if (!guestRepository.existsById(guestId)) {
            throw new GuestNotFoundException(guestId);
        }

        GuestPreference preference =
                guestPreferenceRepository.findByGuestId(guestId)
                        .orElseThrow(() ->
                                new GuestPreferenceNotFoundException(
                                        guestId
                                )
                        );

        return guestMapper.toPreferenceResponse(preference);
    }
}

