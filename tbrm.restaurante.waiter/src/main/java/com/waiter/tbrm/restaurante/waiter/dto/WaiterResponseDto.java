package com.waiter.tbrm.restaurante.waiter.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WaiterResponseDto {

    @NotNull
    private Long id;
    private String run;
    private String name;
    private String lastName;
    private String phone;
    private String email;
    private String shift;
    private String status;
}