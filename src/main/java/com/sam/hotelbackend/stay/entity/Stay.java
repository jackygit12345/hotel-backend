package com.sam.hotelbackend.stay.entity;

import com.sam.hotelbackend.guest.entity.Guest;
import com.sam.hotelbackend.reservation.entity.Reservation;
import com.sam.hotelbackend.room.entity.Room;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "stays",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_stay_reservation",
                        columnNames = "reservation_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "stay_number",
            nullable = false,
            unique = true,
            length = 30
    )
    private String stayNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "reservation_id",
            nullable = false,
            unique = true
    )
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "expected_check_out_date", nullable = false)
    private LocalDate expectedCheckOutDate;

    @Column(name = "actual_check_in_at", nullable = false)
    private LocalDateTime actualCheckInAt;

    @Column(name = "actual_check_out_at")
    private LocalDateTime actualCheckOutAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StayStatus status = StayStatus.IN_HOUSE;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

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