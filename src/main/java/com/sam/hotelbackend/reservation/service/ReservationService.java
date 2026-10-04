package com.sam.hotelbackend.reservation.service;

import com.sam.hotelbackend.reservation.dto.ReservationRequest;
import com.sam.hotelbackend.reservation.dto.ReservationResponse;

import java.util.List;

public interface ReservationService {

    ReservationResponse createReservation(ReservationRequest request);

    ReservationResponse getReservationById(Long id);

    List<ReservationResponse> getAllReservations();

    ReservationResponse updateReservation(Long id, ReservationRequest request);

    void deleteReservation(Long id);

    void confirmReservation(Long id);

    void cancelReservation(Long id);

    void checkInReservation(Long id);

    void checkOutReservation(Long id);

    void markAsNoShow(Long id);	
}