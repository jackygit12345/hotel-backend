package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.guest.entity.GuestStatus;
import com.sam.hotelbackend.guest.entity.GuestType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GuestSearchResponse {

    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private GuestType guestType;
    private GuestStatus status;
}