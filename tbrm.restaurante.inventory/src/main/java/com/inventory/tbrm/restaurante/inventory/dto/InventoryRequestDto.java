package com.inventory.tbrm.restaurante.inventory.dto;
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
public class InventoryRequestDto {

    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @Min(0)
    private Integer stock;
    @NotNull
    @Min(0)
    private Integer minimumStock;
    @NotBlank
    private String unit;
    @NotBlank
    private String status;
}
