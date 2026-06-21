package com.reservation.tbrm.restaurante.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {

    @NotNull
    private Long id;
    @NotNull
    private Long dinerId;
    @NotNull
    private Long tableId;
    @NotBlank
    private String date;
}