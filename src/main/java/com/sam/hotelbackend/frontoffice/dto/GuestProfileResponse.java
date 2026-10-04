package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.guest.dto.GuestDocumentResponse;
import com.sam.hotelbackend.guest.dto.GuestPreferenceResponse;
import com.sam.hotelbackend.reservation.dto.ReservationResponse;
import com.sam.hotelbackend.stay.dto.StayResponse;
import com.sam.hotelbackend.billing.dto.InvoiceResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuestProfileResponse {

    // ============================================================
    // GUEST INFORMATION
    // ============================================================

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String guestType;
    private String status;

    // ============================================================
    // GUEST RELATED INFORMATION
    // ============================================================

    private List<GuestDocumentResponse> documents;
    private GuestPreferenceResponse preference;

    // ============================================================
    // FRONT OFFICE HISTORY
    // ============================================================

    private List<ReservationResponse> reservations;
    private List<StayResponse> stays;
    private List<InvoiceResponse> invoices;
}