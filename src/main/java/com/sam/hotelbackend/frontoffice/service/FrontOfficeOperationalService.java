package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.billing.dto.InvoiceResponse;
import com.sam.hotelbackend.billing.dto.PaymentResponse;
import com.sam.hotelbackend.stay.dto.StayResponse;

import java.util.List;

public interface FrontOfficeOperationalService {

    List<StayResponse> getCurrentInHouseStays();

    List<StayResponse> getStaysByGuest(Long guestId);

    List<StayResponse> getStaysByRoom(Long roomId);

    List<InvoiceResponse> getOpenInvoices();

    List<InvoiceResponse> getPartiallyPaidInvoices();

    List<InvoiceResponse> getOutstandingInvoices();

    List<PaymentResponse> getPaymentsByStatus(String status);
}