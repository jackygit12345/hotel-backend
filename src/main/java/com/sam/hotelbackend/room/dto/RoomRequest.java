package com.sam.hotelbackend.room.dto;

import com.sam.hotelbackend.room.entity.RoomStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomRequest {

    @NotBlank
    @Size(max = 20)
    private String roomNumber;

    @NotNull
    private Long roomTypeId;

    @NotNull
    @Min(0)
    private Integer floor;

    private RoomStatus status;

    @Size(max = 1000)
    private String notes;
}