package com.sam.hotelbackend.frontoffice.controller;

import com.sam.hotelbackend.frontoffice.dto.WalkInGuestRequest;
import com.sam.hotelbackend.frontoffice.dto.WalkInGuestResponse;
import com.sam.hotelbackend.frontoffice.service.FrontOfficeWalkInService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/front-office/walk-in")
@RequiredArgsConstructor
public class FrontOfficeWalkInController {

    private final FrontOfficeWalkInService walkInService;

    @PostMapping
    public ResponseEntity<WalkInGuestResponse> processWalkIn(
            @Valid @RequestBody WalkInGuestRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        walkInService.processWalkIn(request)
                );
    }
}