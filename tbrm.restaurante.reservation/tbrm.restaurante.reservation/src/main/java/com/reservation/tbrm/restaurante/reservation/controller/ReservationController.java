package com.reservation.tbrm.restaurante.reservation.controller;

import com.reservation.tbrm.restaurante.reservation.dto.ReservationRequestDto;
import com.reservation.tbrm.restaurante.reservation.dto.ReservationResponseDto;
import com.reservation.tbrm.restaurante.reservation.service.ReservationService;
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
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@Tag(
        name = "Reservas",
        description = "Endpoints para la gestión de reservas del restaurante"
)
public class ReservationController {

    private final ReservationService service;

    @Operation(
            summary = "Buscar reserva por ID",
            description = "Obtiene una reserva específica mediante su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Listar todas las reservas",
            description = "Obtiene todas las reservas registradas en el sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Reservas listadas correctamente"
    )
    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Buscar reservas por mesa",
            description = "Obtiene todas las reservas asociadas a una mesa específica."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas correctamente"),
            @ApiResponse(responseCode = "400", description = "Mesa no encontrada o solicitud inválida")
    })
    @GetMapping("/by-table/{tableId}")
    public ResponseEntity<List<ReservationResponseDto>> findByTableId(@PathVariable Long tableId) {
        return ResponseEntity.ok(service.findByTableId(tableId));
    }

    @Operation(
            summary = "Buscar reservas por comensal",
            description = "Obtiene todas las reservas asociadas a un comensal específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas correctamente"),
            @ApiResponse(responseCode = "400", description = "Comensal no encontrado o solicitud inválida")
    })
    @GetMapping("/by-diner/{dinerId}")
    public ResponseEntity<List<ReservationResponseDto>> findByDinerId(@PathVariable Long dinerId) {
        return ResponseEntity.ok(service.findByDinerId(dinerId));
    }

    @Operation(
            summary = "Buscar reservas por mesa y comensal",
            description = "Obtiene las reservas filtradas por ID de mesa e ID de comensal."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @GetMapping("/by-table/{tableId}/by-diner/{dinerId}")
    public ResponseEntity<List<ReservationResponseDto>> findByTableIdAndDinerId(
            @PathVariable Long tableId,
            @PathVariable Long dinerId
    ) {
        return ResponseEntity.ok(service.findByTableIdAndDinerId(tableId, dinerId));
    }

    @Operation(
            summary = "Crear reserva",
            description = "Crea una nueva reserva validando el comensal y la mesa asociados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos, comensal no encontrado o mesa no encontrada")
    })
    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@Valid @RequestBody ReservationRequestDto dto) {
        ReservationResponseDto response = service.create(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(null).body(response);
    }

    @Operation(
            summary = "Actualizar reserva",
            description = "Actualiza los datos de una reserva existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o reserva no encontrada")
    })
    @PutMapping
    public ResponseEntity<ReservationResponseDto> update(@Valid @RequestBody ReservationRequestDto dto) {
        ReservationResponseDto response = service.update(dto);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Eliminar reserva",
            description = "Elimina una reserva existente mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de eliminación retornado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }
}