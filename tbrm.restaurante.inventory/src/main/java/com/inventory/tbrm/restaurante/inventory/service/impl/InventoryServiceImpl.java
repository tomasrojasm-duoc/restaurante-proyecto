package com.inventory.tbrm.restaurante.inventory.service.impl;

import com.inventory.tbrm.restaurante.inventory.dto.InventoryRequestDto;
import com.inventory.tbrm.restaurante.inventory.dto.InventoryResponseDto;
import com.inventory.tbrm.restaurante.inventory.model.Inventory;
import com.inventory.tbrm.restaurante.inventory.repository.InventoryRepository;
import com.inventory.tbrm.restaurante.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;

    private InventoryResponseDto toDto(Inventory entity) {
        return new InventoryResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getStock(),
                entity.getMinimumStock(),
                entity.getUnit(),
                entity.getStatus()
        );
    }

    private Inventory toEntity(InventoryRequestDto dto) {
        return new Inventory(
                dto.getId(),
                dto.getName(),
                dto.getStock(),
                dto.getMinimumStock(),
                dto.getUnit(),
                dto.getStatus()
        );
    }

    private String calculateStatus(Integer stock, Integer minimumStock) {
        if (stock == 0) {
            return "OUT_OF_STOCK";
        }

        if (stock <= minimumStock) {
            return "LOW_STOCK";
        }

        return "AVAILABLE";
    }

    @Override
    public List<InventoryResponseDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public InventoryResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public InventoryResponseDto findByName(String name) {
        Inventory item = repository.findByName(name);

        if (item == null) {
            log.warn("Inventory item not found with name {}", name);
            return null;
        }

        return toDto(item);
    }

    @Override
    public List<InventoryResponseDto> findByStatus(String status) {
        return repository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public List<InventoryResponseDto> findByUnit(String unit) {
        return repository.findByUnit(unit).stream().map(this::toDto).toList();
    }

    @Override
    public List<InventoryResponseDto> findByStockLessThanEqual(Integer stock) {
        return repository.findByStockLessThanEqual(stock).stream().map(this::toDto).toList();
    }

    @Override
    public List<InventoryResponseDto> findLowStock() {
        return repository.findAll()
                .stream()
                .filter(item -> item.getStock() <= item.getMinimumStock())
                .map(this::toDto)
                .toList();
    }

    @Override
    public InventoryResponseDto create(InventoryRequestDto dto) {
        if (repository.findByName(dto.getName()) != null) {
            log.warn("Inventory item already exists with name {}", dto.getName());
            return null;
        }

        Inventory item = toEntity(dto);
        item.setStatus(calculateStatus(item.getStock(), item.getMinimumStock()));

        return toDto(repository.save(item));
    }

    @Override
    public InventoryResponseDto update(InventoryRequestDto dto) {
        if (dto.getId() == null) {
            return null;
        }

        if (findById(dto.getId()) == null) {
            return null;
        }

        Inventory item = toEntity(dto);
        item.setStatus(calculateStatus(item.getStock(), item.getMinimumStock()));

        return toDto(repository.save(item));
    }

    @Override
    public InventoryResponseDto updateStock(Long id, Integer stock) {
        Inventory item = repository.findById(id).orElse(null);

        if (item == null) {
            return null;
        }

        item.setStock(stock);
        item.setStatus(calculateStatus(item.getStock(), item.getMinimumStock()));

        return toDto(repository.save(item));
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