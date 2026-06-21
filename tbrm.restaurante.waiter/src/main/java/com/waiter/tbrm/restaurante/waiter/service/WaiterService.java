package com.waiter.tbrm.restaurante.waiter.service;

import com.waiter.tbrm.restaurante.waiter.dto.WaiterRequestDto;
import com.waiter.tbrm.restaurante.waiter.dto.WaiterResponseDto;
import java.util.List;

public interface WaiterService {

    List<WaiterResponseDto> findAll();
    WaiterResponseDto findById(Long id);
    WaiterResponseDto findByRun(String run);
    List<WaiterResponseDto> findByShift(String shift);
    List<WaiterResponseDto> findByStatus(String status);
    List<WaiterResponseDto> findByShiftAndStatus(String shift, String status);
    WaiterResponseDto create(WaiterRequestDto dto);
    WaiterResponseDto update(WaiterRequestDto dto);
    boolean delete(Long id);
}