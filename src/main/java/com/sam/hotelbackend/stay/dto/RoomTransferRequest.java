package com.sam.hotelbackend.stay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomTransferRequest {

    @NotNull
    private Long newRoomId;
}