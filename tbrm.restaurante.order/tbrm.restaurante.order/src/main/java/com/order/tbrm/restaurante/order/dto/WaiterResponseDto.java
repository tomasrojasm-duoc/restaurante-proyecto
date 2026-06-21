package com.order.tbrm.restaurante.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WaiterResponseDto {

    private Long id;
    private String run;
    private String name;
    private String lastName;
    private String phone;
    private String email;
    private String shift;
    private String status;

}