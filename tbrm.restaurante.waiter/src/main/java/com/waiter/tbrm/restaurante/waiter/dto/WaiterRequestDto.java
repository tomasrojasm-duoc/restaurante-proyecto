package com.waiter.tbrm.restaurante.waiter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WaiterRequestDto {

    private Long id;
    @NotBlank
    private String run;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phone;
    @Email
    private String email;
    @NotBlank
    private String shift;
    @NotBlank
    private String status;
}