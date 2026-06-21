package com.diner.tbrm.restaurante.diner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DinerRequestDTO {

    @NotBlank
    String run;
    @NotBlank
    String name;
    @NotBlank
    String lastName;
    @NotBlank
    String phone;
    @NotBlank
    String address;
    @Email
    String email;
    @NotNull
    Date birthday;
}