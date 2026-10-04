package com.sam.hotelbackend.room.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "rooms",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_room_number",
                        columnNames = "room_number"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Room {

    // ============================================================
    // PRIMARY KEY
    // ============================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ============================================================
    // ROOM NUMBER
    // ============================================================

    @Column(
            name = "room_number",
            nullable = false,
            unique = true,
            length = 20
    )
    private String roomNumber;

    // ============================================================
    // ROOM TYPE
    // ============================================================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;

    // ============================================================
    // LOCATION
    // ============================================================

    @Column(nullable = false)
    private Integer floor;

    // ============================================================
    // STATUS
    // ============================================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoomStatus status = RoomStatus.AVAILABLE;

    // ============================================================
    // ADDITIONAL INFORMATION
    // ============================================================

    @Column(length = 1000)
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