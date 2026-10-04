package com.sam.hotelbackend.billing.dto;

import com.sam.hotelbackend.billing.entity.InvoiceItemType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AddInvoiceItemRequest {

    @NotNull
    private InvoiceItemType type;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal quantity;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal unitPrice;
}