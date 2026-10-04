package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.frontoffice.dto.FrontOfficeArrivalResponse;
import com.sam.hotelbackend.frontoffice.dto.FrontOfficeDepartureResponse;
import com.sam.hotelbackend.frontoffice.dto.FrontOfficeInHouseResponse;
import com.sam.hotelbackend.frontoffice.dto.FrontOfficeSummaryResponse;
import com.sam.hotelbackend.frontoffice.dto.RoomStatusResponse;

import java.util.List;

public interface FrontOfficeService {

    FrontOfficeSummaryResponse getSummary();

    List<FrontOfficeArrivalResponse> getTodaysArrivals();

    List<FrontOfficeDepartureResponse> getTodaysDepartures();

    List<FrontOfficeInHouseResponse> getInHouseGuests();

    List<RoomStatusResponse> getAvailableRooms();

    List<RoomStatusResponse> getOccupiedRooms();

    List<RoomStatusResponse> getRoomStatus();
}