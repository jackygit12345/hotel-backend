package com.sam.hotelbackend.guest.dto;

import com.sam.hotelbackend.guest.entity.DocumentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestDocumentResponse {

    private Long id;

    private DocumentType documentType;

    private String documentNumber;

    private String issuingCountry;
}