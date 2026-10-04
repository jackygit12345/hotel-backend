package com.sam.hotelbackend.room.dto;

import com.sam.hotelbackend.room.entity.RoomTypeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RoomTypeResponse {

    private Long id;

    private String name;

    private String description;

    private Integer maxAdults;

    private Integer maxChildren;

    private BigDecimal basePrice;

    private RoomTypeStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}