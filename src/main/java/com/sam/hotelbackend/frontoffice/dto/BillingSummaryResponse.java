package com.sam.hotelbackend.frontoffice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BillingSummaryResponse {

    private long invoiceCount;

    private long openInvoices;

    private long partiallyPaidInvoices;

    private long paidInvoices;

    private BigDecimal grossRevenue = BigDecimal.ZERO;

    private BigDecimal paidAmount = BigDecimal.ZERO;

    private BigDecimal outstandingAmount = BigDecimal.ZERO;
}