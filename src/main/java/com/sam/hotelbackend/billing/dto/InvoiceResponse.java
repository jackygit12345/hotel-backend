package com.sam.hotelbackend.billing.dto;

import com.sam.hotelbackend.billing.entity.InvoiceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceResponse {

    private Long id;

    private String invoiceNumber;

    private Long stayId;
    private String stayNumber;

    private Long guestId;
    private String guestName;

    private BigDecimal subtotal;
    private BigDecimal discountTotal;
    private BigDecimal taxTotal;
    private BigDecimal serviceChargeTotal;
    private BigDecimal grandTotal;

    private BigDecimal paidAmount;
    private BigDecimal balanceAmount;

    private InvoiceStatus status;

    private String notes;

    private List<InvoiceItemResponse> items;

    private List<PaymentResponse> payments;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}