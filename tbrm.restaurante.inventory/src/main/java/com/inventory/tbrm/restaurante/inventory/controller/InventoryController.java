package com.inventory.tbrm.restaurante.inventory.controller;

import com.inventory.tbrm.restaurante.inventory.dto.InventoryRequestDto;
import com.inventory.tbrm.restaurante.inventory.dto.InventoryResponseDto;
import com.inventory.tbrm.restaurante.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<InventoryResponseDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<InventoryResponseDto>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @GetMapping("/by-unit/{unit}")
    public ResponseEntity<List<InventoryResponseDto>> findByUnit(@PathVariable String unit) {
        return ResponseEntity.ok(service.findByUnit(unit));
    }

    @GetMapping("/by-stock-less-than-equal/{stock}")
    public ResponseEntity<List<InventoryResponseDto>> findByStockLessThanEqual(@PathVariable Integer stock) {
        return ResponseEntity.ok(service.findByStockLessThanEqual(stock));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponseDto>> findLowStock() {
        return ResponseEntity.ok(service.findLowStock());
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDto> create(@Valid @RequestBody InventoryRequestDto dto) {
        InventoryResponseDto response = service.create(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(null).body(response);
    }

    @PutMapping
    public ResponseEntity<InventoryResponseDto> update(@Valid @RequestBody InventoryRequestDto dto) {
        InventoryResponseDto response = service.update(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/stock/{stock}")
    public ResponseEntity<InventoryResponseDto> updateStock(
            @PathVariable Long id,
            @PathVariable Integer stock
    ) {
        InventoryResponseDto response = service.updateStock(id, stock);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}