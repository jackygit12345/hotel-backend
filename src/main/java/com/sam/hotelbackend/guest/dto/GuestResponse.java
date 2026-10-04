package com.sam.hotelbackend.guest.dto;

import com.sam.hotelbackend.guest.entity.GuestStatus;
import com.sam.hotelbackend.guest.entity.GuestType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GuestResponse {

    private Long id;

    private GuestType guestType;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String nationality;

    private String preferences;

    private String notes;

    private GuestStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}