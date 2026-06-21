package com.reservation.tbrm.restaurante.reservation.controller;

import com.reservation.tbrm.restaurante.reservation.dto.ReservationRequestDto;
import com.reservation.tbrm.restaurante.reservation.dto.ReservationResponseDto;
import com.reservation.tbrm.restaurante.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/by-table/{tableId}")
    public ResponseEntity<List<ReservationResponseDto>> findByTableId(@PathVariable Long tableId) {
        return ResponseEntity.ok(service.findByTableId(tableId));
    }

    @GetMapping("/by-diner/{dinerId}")
    public ResponseEntity<List<ReservationResponseDto>> findByDinerId(@PathVariable Long dinerId) {
        return ResponseEntity.ok(service.findByDinerId(dinerId));
    }

    @GetMapping("/by-table/{tableId}/by-diner/{dinerId}")
    public ResponseEntity<List<ReservationResponseDto>> findByTableIdAndDinerId(
            @PathVariable Long tableId,
            @PathVariable Long dinerId
    ) {
        return ResponseEntity.ok(service.findByTableIdAndDinerId(tableId, dinerId));
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@Valid @RequestBody ReservationRequestDto dto) {
        ReservationResponseDto response = service.create(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(null).body(response);
    }

    @PutMapping
    public ResponseEntity<ReservationResponseDto> update(@Valid @RequestBody ReservationRequestDto dto) {
        ReservationResponseDto response = service.update(dto);

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