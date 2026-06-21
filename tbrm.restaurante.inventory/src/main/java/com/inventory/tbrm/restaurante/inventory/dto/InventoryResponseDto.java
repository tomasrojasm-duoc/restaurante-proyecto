package com.inventory.tbrm.restaurante.inventory.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDto {

    private Long id;
    private String name;
    private Integer stock;
    private Integer minimumStock;
    private String unit;
    private String status;
}
