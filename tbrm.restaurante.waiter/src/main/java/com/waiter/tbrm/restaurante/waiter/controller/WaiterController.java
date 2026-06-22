package com.waiter.tbrm.restaurante.waiter.controller;

import com.waiter.tbrm.restaurante.waiter.dto.WaiterRequestDto;
import com.waiter.tbrm.restaurante.waiter.dto.WaiterResponseDto;
import com.waiter.tbrm.restaurante.waiter.service.WaiterService;
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
@RequestMapping("/api/v1/waiters")
@RequiredArgsConstructor
public class WaiterController {

    private final WaiterService service;

    @GetMapping("/{id}")
    public ResponseEntity<WaiterResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<WaiterResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-run/{run}")
    public ResponseEntity<WaiterResponseDto> findByRun(@PathVariable String run) {
        return ResponseEntity.ok(service.findByRun(run));
    }

    @GetMapping("/by-shift/{shift}")
    public ResponseEntity<List<WaiterResponseDto>> findByShift(@PathVariable String shift) {
        return ResponseEntity.ok(service.findByShift(shift));
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<WaiterResponseDto>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @GetMapping("/by-shift/{shift}/by-status/{status}")
    public ResponseEntity<List<WaiterResponseDto>> findByShiftAndStatus(
            @PathVariable String shift,
            @PathVariable String status
    ) {
        return ResponseEntity.ok(service.findByShiftAndStatus(shift, status));
    }

    @PostMapping
    public ResponseEntity<WaiterResponseDto> create(@Valid @RequestBody WaiterRequestDto dto) {
        WaiterResponseDto response = service.create(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(null).body(response);
    }

    @PutMapping
    public ResponseEntity<WaiterResponseDto> update(@Valid @RequestBody WaiterRequestDto dto) {
        WaiterResponseDto response = service.update(dto);

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