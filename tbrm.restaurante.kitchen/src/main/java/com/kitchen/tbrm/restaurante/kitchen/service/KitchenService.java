package com.kitchen.tbrm.restaurante.kitchen.service;

import com.kitchen.tbrm.restaurante.kitchen.dto.KitchenRequestDto;
import com.kitchen.tbrm.restaurante.kitchen.dto.KitchenResponseDto;
import java.util.List;

public interface KitchenService {

    List<KitchenResponseDto> findAll();
    KitchenResponseDto findById(Long id);
    KitchenResponseDto findByOrderId(Long orderId);
    List<KitchenResponseDto> findByStatus(String status);
    KitchenResponseDto create(KitchenRequestDto dto);
    KitchenResponseDto update(KitchenRequestDto dto);
    KitchenResponseDto updateStatus(Long id, String status);
    boolean delete(Long id);
}