package com.sam.hotelbackend.guest.dto;

import com.sam.hotelbackend.guest.entity.GuestType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestRequest {

    private GuestType guestType;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @Email
    @Size(max = 150)
    private String email;

    @Size(max = 30)
    private String phone;

    @Size(max = 100)
    private String nationality;

    @Size(max = 500)
    private String preferences;

    @Size(max = 1000)
    private String notes;
}