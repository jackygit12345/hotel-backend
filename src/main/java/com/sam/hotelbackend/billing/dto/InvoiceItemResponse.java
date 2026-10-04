package com.sam.hotelbackend.billing.dto;

import com.sam.hotelbackend.billing.entity.InvoiceItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceItemResponse {

    private Long id;

    private InvoiceItemType type;

    private String description;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal amount;

    private LocalDateTime createdAt;
}