package com.sam.hotelbackend.reservation.entity;

import com.sam.hotelbackend.guest.entity.Guest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "reservations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_reservation_number",
                        columnNames = "reservation_number"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    // ============================================================
    // PRIMARY KEY
    // ============================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ============================================================
    // RESERVATION NUMBER
    // ============================================================

    @Column(
            name = "reservation_number",
            nullable = false,
            unique = true,
            length = 30
    )
    private String reservationNumber;

    // ============================================================
    // GUEST RELATIONSHIP
    // ============================================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    // ============================================================
    // STAY DATES
    // ============================================================

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    // ============================================================
    // GUEST COUNTS
    // ============================================================

    @Column(nullable = false)
    private Integer adults;

    @Column(nullable = false)
    private Integer children;

    // ============================================================
    // ROOM INFORMATION
    // ============================================================

    @Column(name = "room_type", nullable = false, length = 100)
    private String roomType;

    // ============================================================
    // RESERVATION STATUS
    // ============================================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status = ReservationStatus.PENDING;

    // ============================================================
    // RESERVATION SOURCE
    // ============================================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReservationSource source;

    // ============================================================
    // ADDITIONAL INFORMATION
    // ============================================================

    @Column(name = "special_requests", length = 1000)
    private String specialRequests;

    @Column(length = 2000)
    private String notes;

    // ============================================================
    // AUDIT FIELDS
    // ============================================================

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ============================================================
    // JPA LIFECYCLE
    // ============================================================

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}