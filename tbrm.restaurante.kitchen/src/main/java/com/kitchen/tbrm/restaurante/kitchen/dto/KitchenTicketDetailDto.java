package com.kitchen.tbrm.restaurante.kitchen.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenTicketDetailDto {

    private Long id;
    private Long menuItemId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer subtotal;
}

