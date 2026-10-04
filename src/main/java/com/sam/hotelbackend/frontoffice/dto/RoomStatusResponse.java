package com.sam.hotelbackend.frontoffice.dto;

import com.sam.hotelbackend.room.entity.RoomStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomStatusResponse {

    private Long roomId;
    private String roomNumber;

    private Long roomTypeId;
    private String roomTypeName;

    private Integer floor;

    private RoomStatus status;

    private String notes;
}