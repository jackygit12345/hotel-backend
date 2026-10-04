package com.sam.hotelbackend.billing.controller;

import com.sam.hotelbackend.billing.dto.AddInvoiceItemRequest;
import com.sam.hotelbackend.billing.dto.InvoiceResponse;
import com.sam.hotelbackend.billing.dto.PaymentRequest;
import com.sam.hotelbackend.billing.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    // ============================================================
    // CREATE INVOICE FROM STAY
    // ============================================================

    @PostMapping("/invoices/from-stay/{stayId}")
    public ResponseEntity<InvoiceResponse> createInvoiceFromStay(
            @PathVariable Long stayId) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        billingService.createInvoiceFromStay(
                                stayId
                        )
                );
    }

    // ============================================================
    // GET ALL INVOICES
    // ============================================================

    @GetMapping("/invoices")
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {

        return ResponseEntity.ok(
                billingService.getAllInvoices()
        );
    }

    // ============================================================
    // GET INVOICE BY ID
    // ============================================================

    @GetMapping("/invoices/{id}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                billingService.getInvoiceById(id)
        );
    }

    // ============================================================
    // GET INVOICE BY STAY
    // ============================================================

    @GetMapping("/invoices/by-stay/{stayId}")
    public ResponseEntity<InvoiceResponse> getInvoiceByStay(
            @PathVariable Long stayId) {

        return ResponseEntity.ok(
                billingService.getInvoiceByStayId(
                        stayId
                )
        );
    }

    // ============================================================
    // ADD INVOICE ITEM
    // ============================================================

    @PostMapping("/invoices/{invoiceId}/items")
    public ResponseEntity<InvoiceResponse> addInvoiceItem(
            @PathVariable Long invoiceId,
            @Valid @RequestBody AddInvoiceItemRequest request) {

        return ResponseEntity.ok(
                billingService.addInvoiceItem(
                        invoiceId,
                        request
                )
        );
    }

    // ============================================================
    // RECORD PAYMENT
    // ============================================================

    @PostMapping("/invoices/{invoiceId}/payments")
    public ResponseEntity<InvoiceResponse> recordPayment(
            @PathVariable Long invoiceId,
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity.ok(
                billingService.recordPayment(
                        invoiceId,
                        request
                )
        );
    }
}