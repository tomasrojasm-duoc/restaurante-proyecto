package com.order.tbrm.restaurante.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private Long id;
    @NotNull
    private Long dinerId;
    @NotNull
    private Long tableId;
    @NotNull
    private Long waiterId;
    @NotBlank
    private String date;
    private String status;
    @Valid
    @NotEmpty
    private List<OrderDetailRequestDto> details;

}