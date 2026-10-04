package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.billing.entity.Invoice;
import com.sam.hotelbackend.billing.repository.InvoiceItemRepository;
import com.sam.hotelbackend.billing.repository.InvoiceRepository;
import com.sam.hotelbackend.billing.repository.PaymentRepository;
import com.sam.hotelbackend.billing.mapper.BillingMapper;
import com.sam.hotelbackend.billing.dto.InvoiceResponse;

import com.sam.hotelbackend.guest.dto.GuestDocumentResponse;
import com.sam.hotelbackend.guest.entity.Guest;
import com.sam.hotelbackend.guest.entity.GuestPreference;
import com.sam.hotelbackend.guest.exception.GuestNotFoundException;
import com.sam.hotelbackend.guest.mapper.GuestMapper;
import com.sam.hotelbackend.guest.repository.GuestDocumentRepository;
import com.sam.hotelbackend.guest.repository.GuestPreferenceRepository;
import com.sam.hotelbackend.guest.repository.GuestRepository;

import com.sam.hotelbackend.frontoffice.dto.GuestProfileResponse;

import com.sam.hotelbackend.reservation.dto.ReservationResponse;
import com.sam.hotelbackend.reservation.mapper.ReservationMapper;
import com.sam.hotelbackend.reservation.repository.ReservationRepository;

import com.sam.hotelbackend.stay.dto.StayResponse;
import com.sam.hotelbackend.stay.mapper.StayMapper;
import com.sam.hotelbackend.stay.repository.StayRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FrontOfficeGuestServiceImpl
        implements FrontOfficeGuestService {

    private final GuestRepository guestRepository;
    private final GuestDocumentRepository guestDocumentRepository;
    private final GuestPreferenceRepository guestPreferenceRepository;

    private final ReservationRepository reservationRepository;
    private final StayRepository stayRepository;

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final PaymentRepository paymentRepository;

    private final GuestMapper guestMapper;
    private final ReservationMapper reservationMapper;
    private final StayMapper stayMapper;
    private final BillingMapper billingMapper;

    @Override
    public GuestProfileResponse getGuestProfile(Long guestId) {

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(guestId));

        GuestProfileResponse response =
                new GuestProfileResponse();

        // ============================================================
        // GUEST INFORMATION
        // ============================================================

        response.setId(guest.getId());
        response.setFirstName(guest.getFirstName());
        response.setLastName(guest.getLastName());
        response.setEmail(guest.getEmail());
        response.setPhone(guest.getPhone());

        if (guest.getGuestType() != null) {
            response.setGuestType(
                    guest.getGuestType().name()
            );
        }

        if (guest.getStatus() != null) {
            response.setStatus(
                    guest.getStatus().name()
            );
        }

        // ============================================================
        // DOCUMENTS
        // ============================================================

        List<GuestDocumentResponse> documents =
                guestDocumentRepository.findByGuestId(guestId)
                        .stream()
                        .map(guestMapper::toDocumentResponse)
                        .toList();

        response.setDocuments(documents);

        // ============================================================
        // PREFERENCE
        // ============================================================

        GuestPreference preference =
                guestPreferenceRepository.findByGuestId(guestId)
                        .orElse(null);

        if (preference != null) {
            response.setPreference(
                    guestMapper.toPreferenceResponse(preference)
            );
        }

        // ============================================================
        // RESERVATIONS
        // ============================================================

        List<ReservationResponse> reservations =
                reservationRepository.findByGuestId(guestId)
                        .stream()
                        .map(reservationMapper::toResponse)
                        .toList();

        response.setReservations(reservations);

        // ============================================================
        // STAYS
        // ============================================================

        List<StayResponse> stays =
                stayRepository.findByGuestId(guestId)
                        .stream()
                        .map(stayMapper::toResponse)
                        .toList();

        response.setStays(stays);

        // ============================================================
        // INVOICES
        // ============================================================

        List<InvoiceResponse> invoices =
                stays.stream()
                        .map(stay ->
                                invoiceRepository
                                        .findByStayId(stay.getId())
                                        .orElse(null)
                        )
                        .filter(invoice -> invoice != null)
                        .map(invoice ->
                                billingMapper.toInvoiceResponse(
                                        invoice,
                                        invoiceItemRepository
                                                .findByInvoiceId(
                                                        invoice.getId()
                                                ),
                                        paymentRepository
                                                .findByInvoiceId(
                                                        invoice.getId()
                                                )
                                )
                        )
                        .toList();

        response.setInvoices(invoices);

        return response;
    }
}