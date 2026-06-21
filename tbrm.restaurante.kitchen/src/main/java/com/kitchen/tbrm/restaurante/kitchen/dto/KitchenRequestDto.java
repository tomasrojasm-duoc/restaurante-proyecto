package com.kitchen.tbrm.restaurante.kitchen.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KitchenRequestDto {

    private Long id;
    @NotNull
    @Min(1)
    private Long orderId;
    @NotBlank
    private String status;
    @NotNull
    @Min(1)
    private Integer estimatedTime;
    private String observations;
}