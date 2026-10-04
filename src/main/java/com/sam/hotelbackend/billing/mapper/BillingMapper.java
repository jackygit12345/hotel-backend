package com.sam.hotelbackend.billing.mapper;

import com.sam.hotelbackend.billing.dto.InvoiceItemResponse;
import com.sam.hotelbackend.billing.dto.InvoiceResponse;
import com.sam.hotelbackend.billing.dto.PaymentResponse;
import com.sam.hotelbackend.billing.entity.Invoice;
import com.sam.hotelbackend.billing.entity.InvoiceItem;
import com.sam.hotelbackend.billing.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillingMapper {

    public InvoiceItemResponse toItemResponse(
            InvoiceItem item) {

        InvoiceItemResponse response =
                new InvoiceItemResponse();

        response.setId(item.getId());
        response.setType(item.getType());
        response.setDescription(item.getDescription());
        response.setQuantity(item.getQuantity());
        response.setUnitPrice(item.getUnitPrice());
        response.setAmount(item.getAmount());
        response.setCreatedAt(item.getCreatedAt());

        return response;
    }

    public PaymentResponse toPaymentResponse(
            Payment payment) {

        PaymentResponse response =
                new PaymentResponse();

        response.setId(payment.getId());
        response.setPaymentReference(
                payment.getPaymentReference()
        );
        response.setInvoiceId(
                payment.getInvoice().getId()
        );
        response.setAmount(payment.getAmount());
        response.setMethod(payment.getMethod());
        response.setStatus(payment.getStatus());
        response.setReferenceNote(
                payment.getReferenceNote()
        );
        response.setPaidAt(payment.getPaidAt());

        return response;
    }

    public InvoiceResponse toInvoiceResponse(
            Invoice invoice,
            List<InvoiceItem> items,
            List<Payment> payments) {

        InvoiceResponse response =
                new InvoiceResponse();

        response.setId(invoice.getId());
        response.setInvoiceNumber(
                invoice.getInvoiceNumber()
        );

        response.setStayId(
                invoice.getStay().getId()
        );
        response.setStayNumber(
                invoice.getStay().getStayNumber()
        );

        response.setGuestId(
                invoice.getGuest().getId()
        );

        response.setGuestName(
                invoice.getGuest().getFirstName()
                        + " "
                        + invoice.getGuest().getLastName()
        );

        response.setSubtotal(invoice.getSubtotal());
        response.setDiscountTotal(
                invoice.getDiscountTotal()
        );
        response.setTaxTotal(
                invoice.getTaxTotal()
        );
        response.setServiceChargeTotal(
                invoice.getServiceChargeTotal()
        );
        response.setGrandTotal(
                invoice.getGrandTotal()
        );

        response.setPaidAmount(
                invoice.getPaidAmount()
        );
        response.setBalanceAmount(
                invoice.getBalanceAmount()
        );

        response.setStatus(invoice.getStatus());
        response.setNotes(invoice.getNotes());

        response.setItems(
                items.stream()
                        .map(this::toItemResponse)
                        .toList()
        );

        response.setPayments(
                payments.stream()
                        .map(this::toPaymentResponse)
                        .toList()
        );

        response.setCreatedAt(invoice.getCreatedAt());
        response.setUpdatedAt(invoice.getUpdatedAt());

        return response;
    }
}