package com.sam.hotelbackend.guest.dto;

import com.sam.hotelbackend.guest.entity.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestDocumentRequest {

    @NotNull
    private DocumentType documentType;

    @NotBlank
    @Size(max = 100)
    private String documentNumber;

    @Size(max = 100)
    private String issuingCountry;
}