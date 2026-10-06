package com.sam.hotelbackend.frontoffice.service;

import com.sam.hotelbackend.frontoffice.dto.ArrivalReportResponse;
import com.sam.hotelbackend.frontoffice.dto.BillingSummaryResponse;
import com.sam.hotelbackend.frontoffice.dto.DepartureReportResponse;
import com.sam.hotelbackend.frontoffice.dto.InHouseReportResponse;
import com.sam.hotelbackend.frontoffice.dto.OccupancyReportResponse;
import com.sam.hotelbackend.frontoffice.dto.OutstandingBalanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface FrontOfficeReportService {

    OccupancyReportResponse getOccupancyReport();

    List<ArrivalReportResponse> getArrivalReport(
            LocalDate date
    );

    List<DepartureReportResponse> getDepartureReport(
            LocalDate date
    );

    List<InHouseReportResponse> getInHouseReport();

    BillingSummaryResponse getBillingSummary();

    List<OutstandingBalanceResponse> getOutstandingBalances();
}