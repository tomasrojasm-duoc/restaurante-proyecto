package com.reservation.tbrm.restaurante.reservation.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto {

    private Long id;

    @NotNull
    @Digits(integer = 19, fraction = 0, message = "Debes indicar un numérico")
    @Min(1)
    private Long dinerId;

    @NotNull
    @Digits(integer = 19, fraction = 0, message = "Debes indicar un numérico")
    @Min(1)
    private Long tableId;

    @NotBlank
    @Size(min = 10, max = 10)
    private String date;
}