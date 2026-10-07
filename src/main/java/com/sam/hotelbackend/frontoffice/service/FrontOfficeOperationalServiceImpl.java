package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.billing.dto.InvoiceResponse;
import com.sam.hotelbackend.billing.dto.PaymentResponse;
import com.sam.hotelbackend.billing.entity.Invoice;
import com.sam.hotelbackend.billing.entity.PaymentStatus;
import com.sam.hotelbackend.billing.mapper.BillingMapper;
import com.sam.hotelbackend.billing.repository.InvoiceItemRepository;
import com.sam.hotelbackend.billing.repository.InvoiceRepository;
import com.sam.hotelbackend.billing.repository.PaymentRepository;

import com.sam.hotelbackend.stay.dto.StayResponse;
import com.sam.hotelbackend.stay.entity.StayStatus;
import com.sam.hotelbackend.stay.mapper.StayMapper;
import com.sam.hotelbackend.stay.repository.StayRepository;

import com.sam.hotelbackend.frontoffice.dto.GuestStayHistoryResponse;
import com.sam.hotelbackend.guest.dto.GuestResponse;
import com.sam.hotelbackend.guest.service.GuestService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FrontOfficeOperationalServiceImpl
        implements FrontOfficeOperationalService {

    private final StayRepository stayRepository;

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final PaymentRepository paymentRepository;

    private final StayMapper stayMapper;
    private final BillingMapper billingMapper;

    private final GuestService guestService;

    // ============================================================
    // STAY OPERATIONAL SEARCH
    // ============================================================

    @Override
    public List<StayResponse> getCurrentInHouseStays() {

        return stayRepository.findByStatus(StayStatus.IN_HOUSE)
                .stream()
                .map(stayMapper::toResponse)
                .toList();
    }

    @Override
    public List<StayResponse> getStaysByGuest(Long guestId) {

        return stayRepository.findByGuestId(guestId)
                .stream()
                .map(stayMapper::toResponse)
                .toList();
    }

    @Override
    public List<StayResponse> getStaysByRoom(Long roomId) {

        return stayRepository.findByRoomId(roomId)
                .stream()
                .map(stayMapper::toResponse)
                .toList();
    }

    // ============================================================
    // BILLING OPERATIONAL VISIBILITY
    // ============================================================

    @Override
    public List<InvoiceResponse> getOpenInvoices() {

        return invoiceRepository.findByStatus(
                        com.sam.hotelbackend.billing.entity.InvoiceStatus.OPEN
                )
                .stream()
                .map(this::toInvoiceResponse)
                .toList();
    }

    @Override
    public List<InvoiceResponse> getPartiallyPaidInvoices() {

        return invoiceRepository.findByStatus(
                        com.sam.hotelbackend.billing.entity.InvoiceStatus.PARTIALLY_PAID
                )
                .stream()
                .map(this::toInvoiceResponse)
                .toList();
    }

    @Override
    public List<InvoiceResponse> getOutstandingInvoices() {

        return invoiceRepository
                .findByBalanceAmountGreaterThan(BigDecimal.ZERO)
                .stream()
                .map(this::toInvoiceResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> getPaymentsByStatus(String status) {

        PaymentStatus paymentStatus =
                PaymentStatus.valueOf(status.toUpperCase());

        return paymentRepository
                .findByStatus(paymentStatus)
                .stream()
                .map(billingMapper::toPaymentResponse)
                .toList();
    }

    // ============================================================
    // INVOICE MAPPING
    // ============================================================

    private InvoiceResponse toInvoiceResponse(Invoice invoice) {

        return billingMapper.toInvoiceResponse(
                invoice,
                invoiceItemRepository.findByInvoiceId(
                        invoice.getId()
                ),
                paymentRepository.findByInvoiceId(
                        invoice.getId()
                )
        );
    }

    @Override
public GuestStayHistoryResponse getGuestStayHistory(Long guestId) {

    GuestResponse guest = guestService.getGuestById(guestId);

    List<StayResponse> stays =
            stayRepository.findByGuestId(guestId)
                    .stream()
                    .map(stayMapper::toResponse)
                    .toList();

    return new GuestStayHistoryResponse(
            guest.getId(),
            guest.getFirstName() + " " + guest.getLastName(),
            stays
    );
} 

}