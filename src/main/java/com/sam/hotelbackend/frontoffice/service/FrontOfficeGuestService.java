package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.frontoffice.dto.GuestProfileResponse;

public interface FrontOfficeGuestService {

    GuestProfileResponse getGuestProfile(Long guestId);
}