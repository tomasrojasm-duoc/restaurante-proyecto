package com.diner.tbrm.restaurante.diner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DinerResponseDTO {

    Long id;
    String run;
    String name;
    String lastName;
    String phone;
    String address;
    String email;
    Date birthday;
}