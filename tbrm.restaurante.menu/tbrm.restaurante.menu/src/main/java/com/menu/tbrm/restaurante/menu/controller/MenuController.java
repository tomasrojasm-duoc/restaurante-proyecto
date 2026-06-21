package com.menu.tbrm.restaurante.menu.controller;

import com.menu.tbrm.restaurante.menu.dto.MenuItemRequestDto;
import com.menu.tbrm.restaurante.menu.dto.MenuItemResponseDto;
import com.menu.tbrm.restaurante.menu.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/menu-items")
@RequiredArgsConstructor
public class MenuController {

    private final MenuItemService service;

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<MenuItemResponseDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/by-category/{category}")
    public ResponseEntity<List<MenuItemResponseDto>> findByCategory(@PathVariable String category) {
        return ResponseEntity.ok(service.findByCategory(category));
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<MenuItemResponseDto>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @GetMapping("/by-category/{category}/by-status/{status}")
    public ResponseEntity<List<MenuItemResponseDto>> findByCategoryAndStatus(
            @PathVariable String category,
            @PathVariable String status
    ) {
        return ResponseEntity.ok(service.findByCategoryAndStatus(category, status));
    }

    @GetMapping("/by-max-price/{price}")
    public ResponseEntity<List<MenuItemResponseDto>> findByMaxPrice(@PathVariable Integer price) {
        return ResponseEntity.ok(service.findByMaxPrice(price));
    }

    @PostMapping
    public ResponseEntity<MenuItemResponseDto> create(@Valid @RequestBody MenuItemRequestDto dto) {
        MenuItemResponseDto response = service.create(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(null).body(response);
    }

    @PutMapping
    public ResponseEntity<MenuItemResponseDto> update(@Valid @RequestBody MenuItemRequestDto dto) {
        MenuItemResponseDto response = service.update(dto);

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