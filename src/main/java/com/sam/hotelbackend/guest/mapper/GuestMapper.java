package com.sam.hotelbackend.guest.mapper;

import com.sam.hotelbackend.guest.dto.GuestDocumentRequest;
import com.sam.hotelbackend.guest.dto.GuestDocumentResponse;
import com.sam.hotelbackend.guest.dto.GuestPreferenceRequest;
import com.sam.hotelbackend.guest.dto.GuestPreferenceResponse;
import com.sam.hotelbackend.guest.dto.GuestRequest;
import com.sam.hotelbackend.guest.dto.GuestResponse;
import com.sam.hotelbackend.guest.entity.Guest;
import com.sam.hotelbackend.guest.entity.GuestDocument;
import com.sam.hotelbackend.guest.entity.GuestPreference;
import org.springframework.stereotype.Component;

@Component
public class GuestMapper {
    //********************************************************************** 
    //Converts incoming guest data into a JPA entity that can be saved to DB	
    //**********************************************************************
    public Guest toEntity(GuestRequest request) {

        Guest guest = new Guest();

        guest.setGuestType(request.getGuestType());
        guest.setFirstName(request.getFirstName());
        guest.setLastName(request.getLastName());
        guest.setEmail(request.getEmail());
        guest.setPhone(request.getPhone());
        guest.setNationality(request.getNationality());
        guest.setPreferences(request.getPreferences());
        guest.setNotes(request.getNotes());

        return guest;
    }

    //********************************************************
    //Converts DB entity into safe data to send back to frontend
    //********************************************************
    public GuestResponse toResponse(Guest guest) {

        GuestResponse response = new GuestResponse();

        response.setId(guest.getId());
        response.setGuestType(guest.getGuestType());
        response.setFirstName(guest.getFirstName());
        response.setLastName(guest.getLastName());
        response.setEmail(guest.getEmail());
        response.setPhone(guest.getPhone());
        response.setNationality(guest.getNationality());
        response.setPreferences(guest.getPreferences());
        response.setNotes(guest.getNotes());
        response.setStatus(guest.getStatus());
        response.setCreatedAt(guest.getCreatedAt());
        response.setUpdatedAt(guest.getUpdatedAt());

        return response;
    }

    //*****************************************************
    //Converts incoming document data into an entity for DB
    //*****************************************************
    public GuestDocument toDocumentEntity(GuestDocumentRequest request) {

        GuestDocument document = new GuestDocument();

        document.setDocumentType(request.getDocumentType());
        document.setDocumentNumber(request.getDocumentNumber());
        document.setIssuingCountry(request.getIssuingCountry());

        return document;
    }
    
    //********************************************************
    //Converts document entity into response data for frontend
    //********************************************************
    public GuestDocumentResponse toDocumentResponse(GuestDocument document) {

        GuestDocumentResponse response = new GuestDocumentResponse();

        response.setId(document.getId());
        response.setDocumentType(document.getDocumentType());
        response.setDocumentNumber(document.getDocumentNumber());
        response.setIssuingCountry(document.getIssuingCountry());

        return response;
    }

    //******************************************************* 
    //Converts incoming preference data into an entity for DB
    //*******************************************************
    public GuestPreference toPreferenceEntity(GuestPreferenceRequest request) {

        GuestPreference preference = new GuestPreference();

        preference.setRoomPreference(request.getRoomPreference());
        preference.setBedPreference(request.getBedPreference());
        preference.setSmokingPreference(request.getSmokingPreference());
        preference.setDietaryPreference(request.getDietaryPreference());
        preference.setSpecialRequirements(request.getSpecialRequirements());

        return preference;
    }

    //**********************************************************
    //Converts preference entity into response data for frontend
    //**********************************************************
    public GuestPreferenceResponse toPreferenceResponse(GuestPreference preference) {

        GuestPreferenceResponse response = new GuestPreferenceResponse();

        response.setId(preference.getId());
        response.setRoomPreference(preference.getRoomPreference());
        response.setBedPreference(preference.getBedPreference());
        response.setSmokingPreference(preference.getSmokingPreference());
        response.setDietaryPreference(preference.getDietaryPreference());
        response.setSpecialRequirements(preference.getSpecialRequirements());

        return response;
    }
}