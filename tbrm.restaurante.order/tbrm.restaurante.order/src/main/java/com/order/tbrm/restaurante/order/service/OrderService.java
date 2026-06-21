package com.order.tbrm.restaurante.order.service;

import com.order.tbrm.restaurante.order.dto.OrderRequestDto;
import com.order.tbrm.restaurante.order.dto.OrderResponseDto;
import java.util.List;

public interface OrderService {

    List<OrderResponseDto> findAll();
    OrderResponseDto findById(Long id);
    List<OrderResponseDto> findByDinerId(Long dinerId);
    List<OrderResponseDto> findByTableId(Long tableId);
    List<OrderResponseDto> findByWaiterId(Long waiterId);
    List<OrderResponseDto> findByStatus(String status);
    List<OrderResponseDto> findByDate(String date);
    OrderResponseDto create(OrderRequestDto dto);
    OrderResponseDto updateStatus(Long id, String status);
    boolean delete(Long id);
}