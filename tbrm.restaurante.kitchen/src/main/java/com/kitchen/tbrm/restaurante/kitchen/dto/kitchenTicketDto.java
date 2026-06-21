package com.kitchen.tbrm.restaurante.kitchen.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class kitchenTicketDto {

    private Long id;
    private Long dinerId;
    private Long tableId;
    private Long waiterId;
    private String date;
    private String status;
    private Integer total;
    private List<KitchenTicketDetailDto> details;
}