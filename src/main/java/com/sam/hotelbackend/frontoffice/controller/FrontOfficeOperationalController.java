package com.sam.hotelbackend.frontoffice.controller;

import com.sam.hotelbackend.billing.dto.InvoiceResponse;
import com.sam.hotelbackend.billing.dto.PaymentResponse;
import com.sam.hotelbackend.stay.dto.StayResponse;
import com.sam.hotelbackend.frontoffice.service.FrontOfficeOperationalService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/front-office/operational")
@RequiredArgsConstructor
public class FrontOfficeOperationalController {

    private final FrontOfficeOperationalService
            frontOfficeOperationalService;

    // ============================================================
    // CURRENT IN-HOUSE STAYS
    // ============================================================

    @GetMapping("/stays/in-house")
    public ResponseEntity<List<StayResponse>> getCurrentInHouseStays() {

        return ResponseEntity.ok(
                frontOfficeOperationalService
                        .getCurrentInHouseStays()
        );
    }

    // ============================================================
    // STAYS BY GUEST
    // ============================================================

    @GetMapping("/stays/by-guest/{guestId}")
    public ResponseEntity<List<StayResponse>> getStaysByGuest(
            @PathVariable Long guestId) {

        return ResponseEntity.ok(
                frontOfficeOperationalService
                        .getStaysByGuest(guestId)
        );
    }

    // ============================================================
    // STAYS BY ROOM
    // ============================================================

    @GetMapping("/stays/by-room/{roomId}")
    public ResponseEntity<List<StayResponse>> getStaysByRoom(
            @PathVariable Long roomId) {

        return ResponseEntity.ok(
                frontOfficeOperationalService
                        .getStaysByRoom(roomId)
        );
    }

    // ============================================================
    // OPEN INVOICES
    // ============================================================

    @GetMapping("/billing/open-invoices")
    public ResponseEntity<List<InvoiceResponse>> getOpenInvoices() {

        return ResponseEntity.ok(
                frontOfficeOperationalService
                        .getOpenInvoices()
        );
    }

    // ============================================================
    // PARTIALLY PAID INVOICES
    // ============================================================

    @GetMapping("/billing/partially-paid-invoices")
    public ResponseEntity<List<InvoiceResponse>>
    getPartiallyPaidInvoices() {

        return ResponseEntity.ok(
                frontOfficeOperationalService
                        .getPartiallyPaidInvoices()
        );
    }

    // ============================================================
    // OUTSTANDING INVOICES
    // ============================================================

    @GetMapping("/billing/outstanding")
    public ResponseEntity<List<InvoiceResponse>>
    getOutstandingInvoices() {

        return ResponseEntity.ok(
                frontOfficeOperationalService
                        .getOutstandingInvoices()
        );
    }

    // ============================================================
    // PAYMENTS BY STATUS
    // ============================================================

    @GetMapping("/billing/payments/by-status/{status}")
    public ResponseEntity<List<PaymentResponse>>
    getPaymentsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                frontOfficeOperationalService
                        .getPaymentsByStatus(status)
        );
    }
}