package com.sam.hotelbackend.room.dto;

import com.sam.hotelbackend.room.entity.RoomStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RoomResponse {

    private Long id;

    private String roomNumber;

    private Long roomTypeId;

    private String roomTypeName;

    private Integer floor;

    private RoomStatus status;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}