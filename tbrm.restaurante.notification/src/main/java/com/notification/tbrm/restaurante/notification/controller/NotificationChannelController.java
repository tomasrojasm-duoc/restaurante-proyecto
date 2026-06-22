package com.notification.tbrm.restaurante.notification.controller;

import com.notification.tbrm.restaurante.notification.dto.NotificationChannelRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationChannelResponseDto;
import com.notification.tbrm.restaurante.notification.service.NotificationChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/notification-channels")
@RequiredArgsConstructor
public class NotificationChannelController {

    private final NotificationChannelService service;

    @GetMapping
    public ResponseEntity<List<NotificationChannelResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationChannelResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<NotificationChannelResponseDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<NotificationChannelResponseDto>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @PostMapping
    public ResponseEntity<NotificationChannelResponseDto> create(
            @Valid @RequestBody NotificationChannelRequestDto dto
    ) {
        NotificationChannelResponseDto response = service.create(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(null).body(response);
    }

    @PutMapping
    public ResponseEntity<NotificationChannelResponseDto> update(
            @Valid @RequestBody NotificationChannelRequestDto dto
    ) {
        NotificationChannelResponseDto response = service.update(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<NotificationChannelResponseDto> updateStatus(
            @PathVariable Long id,
            @PathVariable String status
    ) {
        NotificationChannelResponseDto response = service.updateStatus(id, status);

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