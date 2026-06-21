package com.menu.tbrm.restaurante.menu.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponseDto {

    @NotNull
    private Long id;
    private String name;
    private String description;
    private String category;
    private Integer price;
    private String status;
}