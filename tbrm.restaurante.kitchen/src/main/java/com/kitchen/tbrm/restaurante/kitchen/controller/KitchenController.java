package com.kitchen.tbrm.restaurante.kitchen.controller;

import com.kitchen.tbrm.restaurante.kitchen.dto.KitchenRequestDto;
import com.kitchen.tbrm.restaurante.kitchen.dto.KitchenResponseDto;
import com.kitchen.tbrm.restaurante.kitchen.service.KitchenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/kitchen-tickets")
@RequiredArgsConstructor
public class KitchenController {

    private final KitchenService service;

    @GetMapping("/{id}")
    public ResponseEntity<KitchenResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<KitchenResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<KitchenResponseDto> findByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(service.findByOrderId(orderId));
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<KitchenResponseDto>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @PostMapping
    public ResponseEntity<KitchenResponseDto> create(@Valid @RequestBody KitchenRequestDto dto) {
        KitchenResponseDto response = service.create(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(null).body(response);
    }

    @PutMapping
    public ResponseEntity<KitchenResponseDto> update(@Valid @RequestBody KitchenRequestDto dto) {
        KitchenResponseDto response = service.update(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<KitchenResponseDto> updateStatus(
            @PathVariable Long id,
            @PathVariable String status
    ) {
        KitchenResponseDto response = service.updateStatus(id, status);

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