package com.sam.hotelbackend.room.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "room_types",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_room_type_name",
                        columnNames = "name"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class RoomType {

    // ============================================================
    // PRIMARY KEY
    // ============================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ============================================================
    // BASIC INFORMATION
    // ============================================================

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 1000)
    private String description;

    // ============================================================
    // CAPACITY
    // ============================================================

    @Column(nullable = false)
    private Integer maxAdults;

    @Column(nullable = false)
    private Integer maxChildren;

    // ============================================================
    // PRICING
    // ============================================================

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    // ============================================================
    // STATUS
    // ============================================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoomTypeStatus status = RoomTypeStatus.ACTIVE;

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