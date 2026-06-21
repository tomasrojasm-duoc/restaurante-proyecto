package com.order.tbrm.restaurante.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long id;
    private Long dinerId;
    private Long tableId;
    private Long waiterId;
    private String date;
    private String status;
    private Integer total;
    private List<OrderDetailResponseDto> details;

}