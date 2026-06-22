package com.notification.tbrm.restaurante.notification.controller;

import com.notification.tbrm.restaurante.notification.dto.NotificationRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationResponseDto;
import com.notification.tbrm.restaurante.notification.service.NotificationService;
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
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-recipient/{recipient}")
    public ResponseEntity<List<NotificationResponseDto>> findByRecipient(@PathVariable String recipient) {
        return ResponseEntity.ok(service.findByRecipient(recipient));
    }

    @GetMapping("/by-type/{type}")
    public ResponseEntity<List<NotificationResponseDto>> findByType(@PathVariable String type) {
        return ResponseEntity.ok(service.findByType(type));
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<NotificationResponseDto>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @GetMapping("/by-recipient/{recipient}/by-status/{status}")
    public ResponseEntity<List<NotificationResponseDto>> findByRecipientAndStatus(
            @PathVariable String recipient,
            @PathVariable String status
    ) {
        return ResponseEntity.ok(service.findByRecipientAndStatus(recipient, status));
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDto> create(@Valid @RequestBody NotificationRequestDto dto) {
        NotificationResponseDto response = service.create(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(null).body(response);
    }

    @PutMapping
    public ResponseEntity<NotificationResponseDto> update(@Valid @RequestBody NotificationRequestDto dto) {
        NotificationResponseDto response = service.update(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<NotificationResponseDto> updateStatus(
            @PathVariable Long id,
            @PathVariable String status
    ) {
        NotificationResponseDto response = service.updateStatus(id, status);

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