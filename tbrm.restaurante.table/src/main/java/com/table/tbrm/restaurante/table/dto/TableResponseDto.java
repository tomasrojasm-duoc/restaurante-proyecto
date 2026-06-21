package com.table.tbrm.restaurante.table.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TableResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private Integer tableNumber;

    @NotNull
    private Integer capacity;

    private String status;
}