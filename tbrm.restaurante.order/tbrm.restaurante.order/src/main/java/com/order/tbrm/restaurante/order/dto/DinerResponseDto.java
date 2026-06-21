package com.order.tbrm.restaurante.order.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class DinerResponseDto {

    private Long id;
    private String run;
    private String name;
    private String lastName;
    private String phone;
    private String address;
    private String email;
    private Date birthday;
}