package com.inventory.tbrm.restaurante.inventory.service;

import com.inventory.tbrm.restaurante.inventory.dto.InventoryRequestDto;
import com.inventory.tbrm.restaurante.inventory.dto.InventoryResponseDto;
import java.util.List;

public interface InventoryService {

    List<InventoryResponseDto> findAll();
    InventoryResponseDto findById(Long id);
    InventoryResponseDto findByName(String name);
    List<InventoryResponseDto> findByStatus(String status);
    List<InventoryResponseDto> findByUnit(String unit);
    List<InventoryResponseDto> findByStockLessThanEqual(Integer stock);
    List<InventoryResponseDto> findLowStock();
    InventoryResponseDto create(InventoryRequestDto dto);
    InventoryResponseDto update(InventoryRequestDto dto);
    InventoryResponseDto updateStock(Long id, Integer stock);
    boolean delete(Long id);
}