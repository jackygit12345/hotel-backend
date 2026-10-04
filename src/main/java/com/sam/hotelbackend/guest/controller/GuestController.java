package com.sam.hotelbackend.guest.controller;

import com.sam.hotelbackend.guest.dto.GuestDocumentRequest;
import com.sam.hotelbackend.guest.dto.GuestDocumentResponse;
import com.sam.hotelbackend.guest.dto.GuestPreferenceRequest;
import com.sam.hotelbackend.guest.dto.GuestPreferenceResponse;
import com.sam.hotelbackend.guest.dto.GuestRequest;
import com.sam.hotelbackend.guest.dto.GuestResponse;
import com.sam.hotelbackend.guest.service.GuestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Guest management.
 *
 * Provides APIs for:
 * - Guest CRUD operations
 * - Guest document management
 * - Guest preference management
 */
@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }


    // ============================================================
    // GUEST CRUD OPERATIONS
    // ============================================================

    /**
     * Create a new guest.
     *
     * HTTP Method: POST
     * Endpoint: /api/guests
     *
     * @param request guest information received from the client
     * @return newly created guest with HTTP 201 CREATED
     */
    @PostMapping
    public ResponseEntity<GuestResponse> createGuest(
            @Valid @RequestBody GuestRequest request) {

        GuestResponse response = guestService.createGuest(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    /**
     * Get a guest by ID.
     *
     * HTTP Method: GET
     * Endpoint: /api/guests/{id}
     *
     * @param id guest ID
     * @return guest information with HTTP 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<GuestResponse> getGuestById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                guestService.getGuestById(id)
        );
    }


    /**
     * Get all guests.
     *
     * HTTP Method: GET
     * Endpoint: /api/guests
     *
     * @return list of all guests with HTTP 200 OK
     */
    @GetMapping
    public ResponseEntity<List<GuestResponse>> getAllGuests() {

        return ResponseEntity.ok(
                guestService.getAllGuests()
        );
    }


    /**
     * Update an existing guest.
     *
     * HTTP Method: PUT
     * Endpoint: /api/guests/{id}
     *
     * @param id guest ID
     * @param request updated guest information
     * @return updated guest with HTTP 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<GuestResponse> updateGuest(
            @PathVariable Long id,
            @Valid @RequestBody GuestRequest request) {

        return ResponseEntity.ok(
                guestService.updateGuest(id, request)
        );
    }


    /**
     * Delete a guest.
     *
     * HTTP Method: DELETE
     * Endpoint: /api/guests/{id}
     *
     * @param id guest ID
     * @return HTTP 204 NO CONTENT when deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(
            @PathVariable Long id) {
	
        guestService.deleteGuest(id);

        return ResponseEntity.noContent().build();
    }


    // ============================================================
    // GUEST DOCUMENT OPERATIONS
    // ============================================================

    /**
     * Add a document to a guest.
     *
     * HTTP Method: POST
     * Endpoint: /api/guests/{guestId}/documents
     *
     * Examples of documents:
     * - Passport
     * - National Identity Card
     * - Driving Licence
     *
     * @param guestId guest ID
     * @param request document information
     * @return newly created document with HTTP 201 CREATED
     */
    @PostMapping("/{guestId}/documents")
    public ResponseEntity<GuestDocumentResponse> addDocument(
            @PathVariable Long guestId,
            @Valid @RequestBody GuestDocumentRequest request) {

        GuestDocumentResponse response =
                guestService.addDocument(guestId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    /**
     * Get all documents belonging to a guest.
     *
     * HTTP Method: GET
     * Endpoint: /api/guests/{guestId}/documents
     *
     * @param guestId guest ID
     * @return list of guest documents with HTTP 200 OK
     */
    @GetMapping("/{guestId}/documents")
    public ResponseEntity<List<GuestDocumentResponse>> getGuestDocuments(
            @PathVariable Long guestId) {

        return ResponseEntity.ok(
                guestService.getGuestDocuments(guestId)
        );
    }


    /**
     * Delete a specific document belonging to a guest.
     *
     * HTTP Method: DELETE
     * Endpoint: /api/guests/{guestId}/documents/{documentId}
     *
     * @param guestId guest ID
     * @param documentId document ID
     * @return HTTP 204 NO CONTENT when deletion is successful
     */
    @DeleteMapping("/{guestId}/documents/{documentId}")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable Long guestId,
            @PathVariable Long documentId) {

        guestService.deleteDocument(guestId, documentId);

        return ResponseEntity.noContent().build();
    }


    // ============================================================
    // GUEST PREFERENCE OPERATIONS
    // ============================================================

    /**
     * Create or update preferences for a guest.
     *
     * HTTP Method: PUT
     * Endpoint: /api/guests/{guestId}/preferences
     *
     * PUT is used because a guest has one preference record,
     * which can be created or updated through the same operation.
     *
     * @param guestId guest ID
     * @param request guest preference information
     * @return saved guest preferences with HTTP 200 OK
     */
    @PutMapping("/{guestId}/preferences")
    public ResponseEntity<GuestPreferenceResponse> savePreferences(
            @PathVariable Long guestId,
            @Valid @RequestBody GuestPreferenceRequest request) {

        return ResponseEntity.ok(
                guestService.savePreferences(guestId, request)
        );
    }


    /**
     * Get preferences for a guest.
     *
     * HTTP Method: GET
     * Endpoint: /api/guests/{guestId}/preferences
     *
     * @param guestId guest ID
     * @return guest preferences with HTTP 200 OK
     */
    @GetMapping("/{guestId}/preferences")
    public ResponseEntity<GuestPreferenceResponse> getPreferences(
            @PathVariable Long guestId) {

        return ResponseEntity.ok(
                guestService.getPreferences(guestId)
        );
    }
}