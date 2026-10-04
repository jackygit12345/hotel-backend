package com.sam.hotelbackend.billing.repository;

import com.sam.hotelbackend.billing.entity.Invoice;
import com.sam.hotelbackend.billing.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    boolean existsByInvoiceNumber(String invoiceNumber);

    Optional<Invoice> findByStayId(Long stayId);

    boolean existsByStayId(Long stayId);

    List<Invoice> findByStatus(InvoiceStatus status);

    List<Invoice> findByBalanceAmountGreaterThan(
          BigDecimal amount
);
}