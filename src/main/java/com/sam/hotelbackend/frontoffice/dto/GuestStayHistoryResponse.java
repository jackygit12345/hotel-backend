package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.stay.dto.StayResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GuestStayHistoryResponse {

    private Long guestId;
    private String guestName;
    private List<StayResponse> stays;
}