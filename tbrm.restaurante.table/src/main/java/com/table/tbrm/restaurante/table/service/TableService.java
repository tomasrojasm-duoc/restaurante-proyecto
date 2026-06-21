package com.table.tbrm.restaurante.table.service;

import com.table.tbrm.restaurante.table.dto.TableRequestDto;
import com.table.tbrm.restaurante.table.dto.TableResponseDto;
import java.util.List;

public interface TableService {

    List<TableResponseDto> findAll();
    TableResponseDto findById(Long id);
    List<TableResponseDto> findByStatus(String status);
    List<TableResponseDto> findByCapacity(Integer capacity);
    TableResponseDto findByTableNumber(Integer tableNumber);
    TableResponseDto create(TableRequestDto dto);
    TableResponseDto update(TableRequestDto dto);
    boolean delete(Long id);
}