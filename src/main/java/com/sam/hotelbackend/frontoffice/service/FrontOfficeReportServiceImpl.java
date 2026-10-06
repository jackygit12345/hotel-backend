package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.billing.entity.Invoice;
import com.sam.hotelbackend.billing.entity.InvoiceStatus;
import com.sam.hotelbackend.billing.repository.InvoiceRepository;

import com.sam.hotelbackend.frontoffice.dto.ArrivalReportResponse;
import com.sam.hotelbackend.frontoffice.dto.BillingSummaryResponse;
import com.sam.hotelbackend.frontoffice.dto.DepartureReportResponse;
import com.sam.hotelbackend.frontoffice.dto.InHouseReportResponse;
import com.sam.hotelbackend.frontoffice.dto.OccupancyReportResponse;
import com.sam.hotelbackend.frontoffice.dto.OutstandingBalanceResponse;

import com.sam.hotelbackend.reservation.entity.Reservation;
import com.sam.hotelbackend.reservation.repository.ReservationRepository;

import com.sam.hotelbackend.room.entity.Room;
import com.sam.hotelbackend.room.entity.RoomStatus;
import com.sam.hotelbackend.room.repository.RoomRepository;

import com.sam.hotelbackend.stay.entity.Stay;
import com.sam.hotelbackend.stay.entity.StayStatus;
import com.sam.hotelbackend.stay.repository.StayRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FrontOfficeReportServiceImpl
        implements FrontOfficeReportService {

    private final ReservationRepository reservationRepository;

    private final StayRepository stayRepository;

    private final RoomRepository roomRepository;

    private final InvoiceRepository invoiceRepository;

    // ============================================================
    // OCCUPANCY REPORT
    // ============================================================

    @Override
    public OccupancyReportResponse getOccupancyReport() {

        List<Room> rooms = roomRepository.findAll();

        OccupancyReportResponse response =
                new OccupancyReportResponse();

        int totalRooms = rooms.size();

        int occupiedRooms = countByStatus(
                rooms,
                RoomStatus.OCCUPIED
        );

        int reservedRooms = countByStatus(
                rooms,
                RoomStatus.RESERVED
        );

        int availableRooms = countByStatus(
                rooms,
                RoomStatus.AVAILABLE
        );

        int cleaningRooms = countByStatus(
                rooms,
                RoomStatus.CLEANING
        );

        int maintenanceRooms = countByStatus(
                rooms,
                RoomStatus.MAINTENANCE
        );

        int outOfOrderRooms = countByStatus(
                rooms,
                RoomStatus.OUT_OF_ORDER
        );

        double occupancyPercentage = 0.0;

        if (totalRooms > 0) {
            occupancyPercentage =
                    ((double) occupiedRooms / totalRooms) * 100;
        }

        response.setTotalRooms(totalRooms);
        response.setOccupiedRooms(occupiedRooms);
        response.setReservedRooms(reservedRooms);
        response.setAvailableRooms(availableRooms);
        response.setCleaningRooms(cleaningRooms);
        response.setMaintenanceRooms(maintenanceRooms);
        response.setOutOfOrderRooms(outOfOrderRooms);
        response.setOccupancyPercentage(
                Math.round(occupancyPercentage * 100.0) / 100.0
        );

        return response;
    }

    // ============================================================
    // ARRIVAL REPORT
    // ============================================================

    @Override
    public List<ArrivalReportResponse> getArrivalReport(
            LocalDate date) {

        return reservationRepository
                .findByCheckInDate(date)
                .stream()
                .map(this::toArrivalResponse)
                .toList();
    }

    // ============================================================
    // DEPARTURE REPORT
    // ============================================================

    @Override
    public List<DepartureReportResponse> getDepartureReport(
            LocalDate date) {

        return stayRepository
                .findByStatus(StayStatus.IN_HOUSE)
                .stream()
                .filter(stay ->
                        stay.getExpectedCheckOutDate()
                                .equals(date)
                )
                .map(this::toDepartureResponse)
                .toList();
    }

    // ============================================================
    // IN-HOUSE REPORT
    // ============================================================

    @Override
    public List<InHouseReportResponse> getInHouseReport() {

        return stayRepository
                .findByStatus(StayStatus.IN_HOUSE)
                .stream()
                .map(this::toInHouseResponse)
                .toList();
    }

    // ============================================================
    // BILLING SUMMARY
    // ============================================================

    @Override
    public BillingSummaryResponse getBillingSummary() {

        List<Invoice> invoices =
                invoiceRepository.findAll();

        BillingSummaryResponse response =
                new BillingSummaryResponse();

        BigDecimal grossRevenue = BigDecimal.ZERO;
        BigDecimal paidAmount = BigDecimal.ZERO;
        BigDecimal outstandingAmount = BigDecimal.ZERO;

        long openInvoices = 0;
        long partiallyPaidInvoices = 0;
        long paidInvoices = 0;

        for (Invoice invoice : invoices) {

            if (invoice.getGrandTotal() != null) {
                grossRevenue = grossRevenue.add(
                        invoice.getGrandTotal()
                );
            }

            if (invoice.getPaidAmount() != null) {
                paidAmount = paidAmount.add(
                        invoice.getPaidAmount()
                );
            }

            if (invoice.getBalanceAmount() != null) {
                outstandingAmount = outstandingAmount.add(
                        invoice.getBalanceAmount()
                );
            }

            if (invoice.getStatus()
                    == InvoiceStatus.OPEN) {

                openInvoices++;

            } else if (invoice.getStatus()
                    == InvoiceStatus.PARTIALLY_PAID) {

                partiallyPaidInvoices++;

            } else if (invoice.getStatus()
                    == InvoiceStatus.PAID) {

                paidInvoices++;
            }
        }

        response.setInvoiceCount(invoices.size());

        response.setOpenInvoices(openInvoices);

        response.setPartiallyPaidInvoices(
                partiallyPaidInvoices
        );

        response.setPaidInvoices(paidInvoices);

        response.setGrossRevenue(grossRevenue);

        response.setPaidAmount(paidAmount);

        response.setOutstandingAmount(
                outstandingAmount
        );

        return response;
    }

    // ============================================================
    // OUTSTANDING BALANCE REPORT
    // ============================================================

    @Override
    public List<OutstandingBalanceResponse>
    getOutstandingBalances() {

        return invoiceRepository
                .findByBalanceAmountGreaterThan(
                        BigDecimal.ZERO
                )
                .stream()
                .map(this::toOutstandingBalanceResponse)
                .toList();
    }

    // ============================================================
    // HELPER - ROOM STATUS COUNT
    // ============================================================

    private int countByStatus(
            List<Room> rooms,
            RoomStatus status) {

        return (int) rooms.stream()
                .filter(room ->
                        room.getStatus() == status
                )
                .count();
    }

    // ============================================================
    // MAPPER - ARRIVAL
    // ============================================================

    private ArrivalReportResponse toArrivalResponse(
            Reservation reservation) {

        ArrivalReportResponse response =
                new ArrivalReportResponse();

        response.setReservationId(
                reservation.getId()
        );

        response.setReservationNumber(
                reservation.getReservationNumber()
        );

        response.setGuestId(
                reservation.getGuest().getId()
        );

        response.setGuestName(
                reservation.getGuest().getFirstName()
                        + " "
                        + reservation.getGuest().getLastName()
        );

        response.setCheckInDate(
                reservation.getCheckInDate()
        );

        response.setCheckOutDate(
                reservation.getCheckOutDate()
        );

        response.setRoomType(
                reservation.getRoomType()
        );

        response.setStatus(
                reservation.getStatus()
        );

        return response;
    }

    // ============================================================
    // MAPPER - DEPARTURE
    // ============================================================

    private DepartureReportResponse toDepartureResponse(
            Stay stay) {

        DepartureReportResponse response =
                new DepartureReportResponse();

        response.setStayId(stay.getId());

        response.setStayNumber(
                stay.getStayNumber()
        );

        response.setGuestId(
                stay.getGuest().getId()
        );

        response.setGuestName(
                stay.getGuest().getFirstName()
                        + " "
                        + stay.getGuest().getLastName()
        );

        response.setReservationId(
                stay.getReservation().getId()
        );

        response.setReservationNumber(
                stay.getReservation()
                        .getReservationNumber()
        );

        response.setRoomId(
                stay.getRoom().getId()
        );

        response.setRoomNumber(
                stay.getRoom().getRoomNumber()
        );

        response.setCheckInDate(
                stay.getCheckInDate()
        );

        response.setExpectedCheckOutDate(
                stay.getExpectedCheckOutDate()
        );

        response.setStatus(
                stay.getStatus()
        );

        return response;
    }

    // ============================================================
    // MAPPER - IN-HOUSE
    // ============================================================

    private InHouseReportResponse toInHouseResponse(
            Stay stay) {

        InHouseReportResponse response =
                new InHouseReportResponse();

        response.setStayId(stay.getId());

        response.setStayNumber(
                stay.getStayNumber()
        );

        response.setGuestId(
                stay.getGuest().getId()
        );

        response.setGuestName(
                stay.getGuest().getFirstName()
                        + " "
                        + stay.getGuest().getLastName()
        );

        response.setRoomId(
                stay.getRoom().getId()
        );

        response.setRoomNumber(
                stay.getRoom().getRoomNumber()
        );

        response.setRoomType(
                stay.getRoom()
                        .getRoomType()
                        .getName()
        );

        response.setCheckInDate(
                stay.getCheckInDate()
        );

        response.setExpectedCheckOutDate(
                stay.getExpectedCheckOutDate()
        );

        response.setActualCheckInAt(
                stay.getActualCheckInAt()
        );

        response.setStatus(
                stay.getStatus()
        );

        return response;
    }

    // ============================================================
    // MAPPER - OUTSTANDING BALANCE
    // ============================================================

    private OutstandingBalanceResponse
    toOutstandingBalanceResponse(
            Invoice invoice) {

        OutstandingBalanceResponse response =
                new OutstandingBalanceResponse();

        response.setInvoiceId(
                invoice.getId()
        );

        response.setInvoiceNumber(
                invoice.getInvoiceNumber()
        );

        response.setGuestId(
                invoice.getGuest().getId()
        );

        response.setGuestName(
                invoice.getGuest().getFirstName()
                        + " "
                        + invoice.getGuest().getLastName()
        );

        response.setStayId(
                invoice.getStay().getId()
        );

        response.setStayNumber(
                invoice.getStay().getStayNumber()
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

        return response;
    }
}