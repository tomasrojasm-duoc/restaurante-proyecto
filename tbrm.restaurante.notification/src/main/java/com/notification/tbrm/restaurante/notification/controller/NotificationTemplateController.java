package com.notification.tbrm.restaurante.notification.controller;

import com.notification.tbrm.restaurante.notification.dto.NotificationTemplateRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationTemplateResponseDto;
import com.notification.tbrm.restaurante.notification.service.NotificationTemplateService;
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
@RequestMapping("/api/v1/notification-templates")
@RequiredArgsConstructor
public class NotificationTemplateController {

    private final NotificationTemplateService service;

    @GetMapping
    public ResponseEntity<List<NotificationTemplateResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationTemplateResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-type/{type}")
    public ResponseEntity<NotificationTemplateResponseDto> findByType(@PathVariable String type) {
        return ResponseEntity.ok(service.findByType(type));
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<NotificationTemplateResponseDto>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @PostMapping
    public ResponseEntity<NotificationTemplateResponseDto> create(
            @Valid @RequestBody NotificationTemplateRequestDto dto
    ) {
        NotificationTemplateResponseDto response = service.create(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(null).body(response);
    }

    @PutMapping
    public ResponseEntity<NotificationTemplateResponseDto> update(
            @Valid @RequestBody NotificationTemplateRequestDto dto
    ) {
        NotificationTemplateResponseDto response = service.update(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<NotificationTemplateResponseDto> updateStatus(
            @PathVariable Long id,
            @PathVariable String status
    ) {
        NotificationTemplateResponseDto response = service.updateStatus(id, status);

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