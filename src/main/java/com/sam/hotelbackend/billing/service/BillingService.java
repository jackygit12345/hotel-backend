package com.sam.hotelbackend.billing.service;

import com.sam.hotelbackend.billing.dto.AddInvoiceItemRequest;
import com.sam.hotelbackend.billing.dto.InvoiceResponse;
import com.sam.hotelbackend.billing.dto.PaymentRequest;

import java.util.List;

public interface BillingService {

    InvoiceResponse createInvoiceFromStay(Long stayId);

    InvoiceResponse getInvoiceById(Long id);

    InvoiceResponse getInvoiceByStayId(Long stayId);

    List<InvoiceResponse> getAllInvoices();

    InvoiceResponse addInvoiceItem(
            Long invoiceId,
            AddInvoiceItemRequest request
    );

    InvoiceResponse recordPayment(
            Long invoiceId,
            PaymentRequest request
    );
}