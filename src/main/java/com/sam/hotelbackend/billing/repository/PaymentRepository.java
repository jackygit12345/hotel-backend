package com.sam.hotelbackend.billing.repository;

import com.sam.hotelbackend.billing.entity.Payment;
import com.sam.hotelbackend.billing.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    boolean existsByPaymentReference(String paymentReference);

    List<Payment> findByInvoiceId(Long invoiceId);

    List<Payment> findByInvoiceIdAndStatus(
            Long invoiceId,
            PaymentStatus status
    );

    List<Payment> findByStatus(PaymentStatus status);
}