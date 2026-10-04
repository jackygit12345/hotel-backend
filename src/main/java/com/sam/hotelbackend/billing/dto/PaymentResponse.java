package com.sam.hotelbackend.billing.dto;

import com.sam.hotelbackend.billing.entity.PaymentMethod;
import com.sam.hotelbackend.billing.entity.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponse {

    private Long id;

    private String paymentReference;

    private Long invoiceId;

    private BigDecimal amount;

    private PaymentMethod method;

    private PaymentStatus status;

    private String referenceNote;

    private LocalDateTime paidAt;
}