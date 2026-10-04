package com.sam.hotelbackend.frontoffice.controller;

import com.sam.hotelbackend.frontoffice.dto.GuestProfileResponse;
import com.sam.hotelbackend.frontoffice.service.FrontOfficeGuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/front-office/guests")
@RequiredArgsConstructor
public class FrontOfficeGuestController {

    private final FrontOfficeGuestService frontOfficeGuestService;

    // ============================================================
    // GUEST 360° PROFILE
    // ============================================================

    @GetMapping("/{guestId}/profile")
    public ResponseEntity<GuestProfileResponse> getGuestProfile(
            @PathVariable Long guestId) {

        return ResponseEntity.ok(
                frontOfficeGuestService.getGuestProfile(guestId)
        );
    }
}