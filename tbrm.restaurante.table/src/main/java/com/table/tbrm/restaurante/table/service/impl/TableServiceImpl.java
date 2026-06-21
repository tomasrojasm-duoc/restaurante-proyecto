package com.table.tbrm.restaurante.table.service.impl;

import com.table.tbrm.restaurante.table.dto.TableRequestDto;
import com.table.tbrm.restaurante.table.dto.TableResponseDto;
import com.table.tbrm.restaurante.table.model.TableRestaurant;
import com.table.tbrm.restaurante.table.repository.TableRepository;
import com.table.tbrm.restaurante.table.service.TableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TableServiceImpl implements TableService {

    private final TableRepository repository;

    private TableResponseDto toDto(TableRestaurant entity) {
        return new TableResponseDto(
                entity.getId(),
                entity.getTableNumber(),
                entity.getCapacity(),
                entity.getStatus()
        );
    }

    private TableRestaurant toEntity(TableRequestDto dto) {
        return new TableRestaurant(
                dto.getId(),
                dto.getTableNumber(),
                dto.getCapacity(),
                dto.getStatus()
        );
    }

    @Override
    public List<TableResponseDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public TableResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<TableResponseDto> findByStatus(String status) {
        return repository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public List<TableResponseDto> findByCapacity(Integer capacity) {
        return repository.findByCapacityGreaterThanEqual(capacity).stream().map(this::toDto).toList();
    }

    @Override
    public TableResponseDto findByTableNumber(Integer tableNumber) {
        TableRestaurant table = repository.findByTableNumber(tableNumber);

        if (table == null) {
            log.warn("Table not found with table number {}", tableNumber);
            return null;
        }

        return toDto(table);
    }

    @Override
    public TableResponseDto create(TableRequestDto dto) {
        if (repository.findByTableNumber(dto.getTableNumber()) != null) {
            log.warn("Table already exists with table number {}", dto.getTableNumber());
            return null;
        }

        return this.toDto(repository.save(toEntity(dto)));
    }

    @Override
    public TableResponseDto update(TableRequestDto dto) {
        if (dto.getId() == null) {
            return null;
        }

        if (findById(dto.getId()) == null) {
            return null;
        }

        return this.toDto(repository.save(toEntity(dto)));
    }

    @Override
    public boolean delete(Long id) {
        if (findById(id) == null) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}