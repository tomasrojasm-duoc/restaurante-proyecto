package com.order.tbrm.restaurante.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponseDto {

    private Long id;
    private Long menuItemId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer subtotal;
}