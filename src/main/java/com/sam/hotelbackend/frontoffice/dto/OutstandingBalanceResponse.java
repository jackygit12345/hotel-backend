package com.sam.hotelbackend.frontoffice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OutstandingBalanceResponse {

    private Long invoiceId;

    private String invoiceNumber;

    private Long guestId;

    private String guestName;

    private Long stayId;

    private String stayNumber;

    private BigDecimal grandTotal;

    private BigDecimal paidAmount;

    private BigDecimal balanceAmount;
}