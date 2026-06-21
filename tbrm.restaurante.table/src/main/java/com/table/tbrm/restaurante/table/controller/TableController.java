package com.table.tbrm.restaurante.table.controller;

import com.table.tbrm.restaurante.table.dto.TableRequestDto;
import com.table.tbrm.restaurante.table.dto.TableResponseDto;
import com.table.tbrm.restaurante.table.service.TableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService service;

    @GetMapping("/{id}")
    public ResponseEntity<TableResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<TableResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<TableResponseDto>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @GetMapping("/by-capacity/{capacity}")
    public ResponseEntity<List<TableResponseDto>> findByCapacity(@PathVariable Integer capacity) {
        return ResponseEntity.ok(service.findByCapacity(capacity));
    }

    @GetMapping("/by-number/{tableNumber}")
    public ResponseEntity<TableResponseDto> findByTableNumber(@PathVariable Integer tableNumber) {
        return ResponseEntity.ok(service.findByTableNumber(tableNumber));
    }

    @PostMapping
    public ResponseEntity<TableResponseDto> create(@Valid @RequestBody TableRequestDto dto) {
        TableResponseDto response = service.create(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(null).body(response);
    }

    @PutMapping
    public ResponseEntity<TableResponseDto> update(@Valid @RequestBody TableRequestDto dto) {
        TableResponseDto response = service.update(dto);

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