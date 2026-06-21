package com.diner.tbrm.restaurante.diner.controller;

import com.diner.tbrm.restaurante.diner.dto.DinerRequestDTO;
import com.diner.tbrm.restaurante.diner.dto.DinerResponseDTO;
import com.diner.tbrm.restaurante.diner.service.DinerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diners")
@RequiredArgsConstructor
public class DinerController {

    private final DinerService service;

    @GetMapping
    public ResponseEntity<List<DinerResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DinerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<DinerResponseDTO> getByRun(@PathVariable String run) {
        return ResponseEntity.ok(service.getByRun(run));
    }

    @PostMapping
    public ResponseEntity<DinerResponseDTO> save(@Valid @RequestBody DinerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DinerResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody DinerRequestDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
}