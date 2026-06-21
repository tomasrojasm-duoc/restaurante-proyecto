package com.kitchen.tbrm.restaurante.kitchen.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KitchenResponseDto {

    private Long id;
    private Long orderId;
    private String status;
    private Integer estimatedTime;
    private String observations;
}