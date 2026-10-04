package com.sam.hotelbackend.guest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guest_preferences")
@Getter
@Setter
@NoArgsConstructor
public class GuestPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "guest_id", nullable = false, unique = true)
    private Guest guest;

    @Column(length = 100)
    private String roomPreference;

    @Column(length = 100)
    private String bedPreference;

    @Column(length = 50)
    private String smokingPreference;

    @Column(length = 500)
    private String dietaryPreference;

    @Column(length = 1000)
    private String specialRequirements;
}