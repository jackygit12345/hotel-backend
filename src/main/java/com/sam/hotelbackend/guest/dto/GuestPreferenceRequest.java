package com.sam.hotelbackend.guest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GuestPreferenceRequest {

    private String roomPreference;

    private String bedPreference;

    private String smokingPreference;

    private String dietaryPreference;

    private String specialRequirements;
}