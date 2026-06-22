package com.diner.tbrm.restaurante.diner.controller;

import com.diner.tbrm.restaurante.diner.dto.DinerRequestDTO;
import com.diner.tbrm.restaurante.diner.dto.DinerResponseDTO;
import com.diner.tbrm.restaurante.diner.service.DinerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diners")
@RequiredArgsConstructor
@Tag(
        name = "Comensales",
        description = "Endpoints para la gestión de comensales del restaurante"
)
public class DinerController {

    private final DinerService service;

    @Operation(
            summary = "Listar todos los comensales",
            description = "Obtiene todos los comensales registrados en el sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Comensales listados correctamente"
    )
    @GetMapping
    public ResponseEntity<List<DinerResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Buscar comensal por ID",
            description = "Obtiene un comensal específico mediante su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comensal encontrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DinerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(
            summary = "Buscar comensal por RUN",
            description = "Obtiene un comensal específico mediante su RUN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comensal encontrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @GetMapping("/run/{run}")
    public ResponseEntity<DinerResponseDTO> getByRun(@PathVariable String run) {
        return ResponseEntity.ok(service.getByRun(run));
    }

    @Operation(
            summary = "Crear comensal",
            description = "Registra un nuevo comensal en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comensal creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<DinerResponseDTO> save(@Valid @RequestBody DinerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @Operation(
            summary = "Actualizar comensal",
            description = "Actualiza los datos de un comensal existente mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comensal actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DinerResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody DinerRequestDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(
            summary = "Eliminar comensal",
            description = "Elimina un comensal existente mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de eliminación retornado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
}