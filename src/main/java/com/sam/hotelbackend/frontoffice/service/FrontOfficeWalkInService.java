package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.frontoffice.dto.WalkInGuestRequest;
import com.sam.hotelbackend.frontoffice.dto.WalkInGuestResponse;

public interface FrontOfficeWalkInService {

    WalkInGuestResponse processWalkIn(
            WalkInGuestRequest request
    );
}