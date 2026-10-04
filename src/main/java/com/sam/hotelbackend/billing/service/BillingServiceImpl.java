package com.sam.hotelbackend.billing.service;

import com.sam.hotelbackend.billing.dto.AddInvoiceItemRequest;
import com.sam.hotelbackend.billing.dto.InvoiceResponse;
import com.sam.hotelbackend.billing.dto.PaymentRequest;
import com.sam.hotelbackend.billing.entity.Invoice;
import com.sam.hotelbackend.billing.entity.InvoiceItem;
import com.sam.hotelbackend.billing.entity.InvoiceItemType;
import com.sam.hotelbackend.billing.entity.InvoiceStatus;
import com.sam.hotelbackend.billing.entity.Payment;
import com.sam.hotelbackend.billing.entity.PaymentStatus;
import com.sam.hotelbackend.billing.exception.InvalidBillingOperationException;
import com.sam.hotelbackend.billing.exception.InvoiceNotFoundException;
import com.sam.hotelbackend.billing.mapper.BillingMapper;
import com.sam.hotelbackend.billing.repository.InvoiceItemRepository;
import com.sam.hotelbackend.billing.repository.InvoiceRepository;
import com.sam.hotelbackend.billing.repository.PaymentRepository;
import com.sam.hotelbackend.room.entity.Room;
import com.sam.hotelbackend.stay.entity.Stay;
import com.sam.hotelbackend.stay.entity.StayStatus;
import com.sam.hotelbackend.stay.repository.StayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BillingServiceImpl implements BillingService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final PaymentRepository paymentRepository;
    private final StayRepository stayRepository;
    private final BillingMapper billingMapper;

    @Override
    public InvoiceResponse createInvoiceFromStay(
            Long stayId) {

        Stay stay = stayRepository
                .findById(stayId)
                .orElseThrow(() ->
                        new InvalidBillingOperationException(
                                "Stay not found with id: "
                                        + stayId
                        )
                );

        if (invoiceRepository.existsByStayId(stayId)) {
            throw new InvalidBillingOperationException(
                    "An invoice already exists for this stay"
            );
        }

        Room room = stay.getRoom();

        long nights = ChronoUnit.DAYS.between(
                stay.getCheckInDate(),
                stay.getExpectedCheckOutDate()
        );

        if (nights <= 0) {
            nights = 1;
        }

        BigDecimal roomRate =
                room.getRoomType().getBasePrice();

        BigDecimal roomCharge =
                roomRate.multiply(
                        BigDecimal.valueOf(nights)
                );

        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber(
                generateInvoiceNumber()
        );

        invoice.setStay(stay);
        invoice.setGuest(stay.getGuest());

        invoice.setSubtotal(roomCharge);
        invoice.setDiscountTotal(BigDecimal.ZERO);
        invoice.setTaxTotal(BigDecimal.ZERO);
        invoice.setServiceChargeTotal(BigDecimal.ZERO);

        invoice.setGrandTotal(roomCharge);
        invoice.setPaidAmount(BigDecimal.ZERO);
        invoice.setBalanceAmount(roomCharge);

        invoice.setStatus(InvoiceStatus.OPEN);

        Invoice savedInvoice =
                invoiceRepository.save(invoice);

        InvoiceItem roomItem = new InvoiceItem();

        roomItem.setInvoice(savedInvoice);
        roomItem.setType(InvoiceItemType.ROOM_CHARGE);
        roomItem.setDescription(
                "Room charge - "
                        + room.getRoomNumber()
                        + " for "
                        + nights
                        + " night(s)"
        );
        roomItem.setQuantity(
                BigDecimal.valueOf(nights)
        );
        roomItem.setUnitPrice(roomRate);
        roomItem.setAmount(roomCharge);

        invoiceItemRepository.save(roomItem);

        return buildResponse(savedInvoice);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse getInvoiceById(Long id) {

        Invoice invoice = invoiceRepository
                .findById(id)
                .orElseThrow(() ->
                        new InvoiceNotFoundException(id)
                );

        return buildResponse(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse getInvoiceByStayId(
            Long stayId) {

        Invoice invoice = invoiceRepository
                .findByStayId(stayId)
                .orElseThrow(() ->
                        new InvalidBillingOperationException(
                                "Invoice not found for stay id: "
                                        + stayId
                        )
                );

        return buildResponse(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAllInvoices() {

        return invoiceRepository.findAll()
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    public InvoiceResponse addInvoiceItem(
            Long invoiceId,
            AddInvoiceItemRequest request) {

        Invoice invoice = getInvoice(invoiceId);

        if (invoice.getStatus() == InvoiceStatus.PAID
                || invoice.getStatus() == InvoiceStatus.VOID) {

            throw new InvalidBillingOperationException(
                    "Cannot add items to a "
                            + invoice.getStatus()
                            + " invoice"
            );
        }

        BigDecimal amount =
                request.getQuantity()
                        .multiply(request.getUnitPrice())
                        .setScale(2, RoundingMode.HALF_UP);

        InvoiceItem item = new InvoiceItem();

        item.setInvoice(invoice);
        item.setType(request.getType());
        item.setDescription(
                request.getDescription()
        );
        item.setQuantity(request.getQuantity());
        item.setUnitPrice(request.getUnitPrice());
        item.setAmount(amount);

        invoiceItemRepository.save(item);

        recalculateInvoice(invoice);

        return buildResponse(invoice);
    }

    @Override
    public InvoiceResponse recordPayment(
            Long invoiceId,
            PaymentRequest request) {

        Invoice invoice = getInvoice(invoiceId);

        if (invoice.getStatus() == InvoiceStatus.VOID) {
            throw new InvalidBillingOperationException(
                    "Cannot make a payment against a VOID invoice"
            );
        }

        if (invoice.getStatus() == InvoiceStatus.PAID) {
            throw new InvalidBillingOperationException(
                    "Invoice is already fully paid"
            );
        }

        BigDecimal amount =
                request.getAmount()
                        .setScale(2, RoundingMode.HALF_UP);

        if (amount.compareTo(
                invoice.getBalanceAmount()) > 0) {

            throw new InvalidBillingOperationException(
                    "Payment amount cannot exceed the outstanding balance"
            );
        }

        Payment payment = new Payment();

        payment.setPaymentReference(
                generatePaymentReference()
        );

        payment.setInvoice(invoice);
        payment.setAmount(amount);
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setReferenceNote(
                request.getReferenceNote()
        );

        paymentRepository.save(payment);

        invoice.setPaidAmount(
                invoice.getPaidAmount()
                        .add(amount)
        );

        invoice.setBalanceAmount(
                invoice.getGrandTotal()
                        .subtract(invoice.getPaidAmount())
                        .max(BigDecimal.ZERO)
        );

        updateInvoicePaymentStatus(invoice);

        return buildResponse(invoice);
    }

    private Invoice getInvoice(Long id) {

        return invoiceRepository
                .findById(id)
                .orElseThrow(() ->
                        new InvoiceNotFoundException(id)
                );
    }

    private void recalculateInvoice(Invoice invoice) {

        List<InvoiceItem> items =
                invoiceItemRepository.findByInvoiceId(
                        invoice.getId()
                );

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal discountTotal = BigDecimal.ZERO;
        BigDecimal taxTotal = BigDecimal.ZERO;
        BigDecimal serviceChargeTotal =
                BigDecimal.ZERO;

        for (InvoiceItem item : items) {

            BigDecimal amount = item.getAmount();

            if (item.getType()
                    == InvoiceItemType.DISCOUNT) {

                discountTotal =
                        discountTotal.add(amount);

            } else if (item.getType()
                    == InvoiceItemType.TAX) {

                taxTotal =
                        taxTotal.add(amount);

            } else if (item.getType()
                    == InvoiceItemType.SERVICE_CHARGE) {

                serviceChargeTotal =
                        serviceChargeTotal.add(amount);

            } else {

                subtotal =
                        subtotal.add(amount);
            }
        }

        BigDecimal grandTotal =
                subtotal
                        .subtract(discountTotal)
                        .add(taxTotal)
                        .add(serviceChargeTotal)
                        .max(BigDecimal.ZERO);

        invoice.setSubtotal(subtotal);
        invoice.setDiscountTotal(discountTotal);
        invoice.setTaxTotal(taxTotal);
        invoice.setServiceChargeTotal(
                serviceChargeTotal
        );
        invoice.setGrandTotal(grandTotal);

        invoice.setBalanceAmount(
                grandTotal
                        .subtract(invoice.getPaidAmount())
                        .max(BigDecimal.ZERO)
        );

        updateInvoicePaymentStatus(invoice);
    }

    private void updateInvoicePaymentStatus(
            Invoice invoice) {

        if (invoice.getPaidAmount()
                .compareTo(BigDecimal.ZERO) == 0) {

            invoice.setStatus(InvoiceStatus.OPEN);

        } else if (invoice.getPaidAmount()
                .compareTo(invoice.getGrandTotal()) >= 0) {

            invoice.setStatus(InvoiceStatus.PAID);

        } else {

            invoice.setStatus(
                    InvoiceStatus.PARTIALLY_PAID
            );
        }
    }

    private InvoiceResponse buildResponse(
            Invoice invoice) {

        List<InvoiceItem> items =
                invoiceItemRepository.findByInvoiceId(
                        invoice.getId()
                );

        List<Payment> payments =
                paymentRepository.findByInvoiceId(
                        invoice.getId()
                );

        return billingMapper.toInvoiceResponse(
                invoice,
                items,
                payments
        );
    }

    private String generateInvoiceNumber() {

        String datePart =
                LocalDate.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyyMMdd"
                                )
                        );

        long count =
                invoiceRepository.count() + 1;

        return String.format(
                "INV-%s-%04d",
                datePart,
                count
        );
    }

    private String generatePaymentReference() {

        String datePart =
                LocalDate.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyyMMdd"
                                )
                        );

        long count =
                paymentRepository.count() + 1;

        return String.format(
                "PAY-%s-%04d",
                datePart,
                count
        );
    }
}