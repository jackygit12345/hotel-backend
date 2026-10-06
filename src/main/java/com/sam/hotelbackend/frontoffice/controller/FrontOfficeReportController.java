package com.sam.hotelbackend.frontoffice.controller;

import com.sam.hotelbackend.frontoffice.dto.ArrivalReportResponse;
import com.sam.hotelbackend.frontoffice.dto.BillingSummaryResponse;
import com.sam.hotelbackend.frontoffice.dto.DepartureReportResponse;
import com.sam.hotelbackend.frontoffice.dto.InHouseReportResponse;
import com.sam.hotelbackend.frontoffice.dto.OccupancyReportResponse;
import com.sam.hotelbackend.frontoffice.dto.OutstandingBalanceResponse;
import com.sam.hotelbackend.frontoffice.service.FrontOfficeReportService;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/front-office/reports")
@RequiredArgsConstructor
public class FrontOfficeReportController {

    private final FrontOfficeReportService
            frontOfficeReportService;

    // ============================================================
    // OCCUPANCY
    // ============================================================

    @GetMapping("/occupancy")
    public ResponseEntity<OccupancyReportResponse>
    getOccupancyReport() {

        return ResponseEntity.ok(
                frontOfficeReportService
                        .getOccupancyReport()
        );
    }

    // ============================================================
    // ARRIVALS
    // ============================================================

    @GetMapping("/arrivals")
    public ResponseEntity<List<ArrivalReportResponse>>
    getArrivalReport(
            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE
            )
            LocalDate date) {

        return ResponseEntity.ok(
                frontOfficeReportService
                        .getArrivalReport(date)
        );
    }

    // ============================================================
    // DEPARTURES
    // ============================================================

    @GetMapping("/departures")
    public ResponseEntity<List<DepartureReportResponse>>
    getDepartureReport(
            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE
            )
            LocalDate date) {

        return ResponseEntity.ok(
                frontOfficeReportService
                        .getDepartureReport(date)
        );
    }

    // ============================================================
    // IN-HOUSE
    // ============================================================

    @GetMapping("/in-house")
    public ResponseEntity<List<InHouseReportResponse>>
    getInHouseReport() {

        return ResponseEntity.ok(
                frontOfficeReportService
                        .getInHouseReport()
        );
    }

    // ============================================================
    // BILLING SUMMARY
    // ============================================================

    @GetMapping("/billing-summary")
    public ResponseEntity<BillingSummaryResponse>
    getBillingSummary() {

        return ResponseEntity.ok(
                frontOfficeReportService
                        .getBillingSummary()
        );
    }

    // ============================================================
    // OUTSTANDING BALANCES
    // ============================================================

    @GetMapping("/outstanding-balances")
    public ResponseEntity<List<OutstandingBalanceResponse>>
    getOutstandingBalances() {

        return ResponseEntity.ok(
                frontOfficeReportService
                        .getOutstandingBalances()
        );
    }
}