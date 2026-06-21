package com.order.tbrm.restaurante.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuItemResponseDto {

    private Long id;
    private String name;
    private String description;
    private String category;
    private Integer price;
    private String status;
}