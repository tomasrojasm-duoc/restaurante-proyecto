package com.order.tbrm.restaurante.order.controller;

import com.order.tbrm.restaurante.order.dto.OrderRequestDto;
import com.order.tbrm.restaurante.order.dto.OrderResponseDto;
import com.order.tbrm.restaurante.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(
        name = "Pedidos",
        description = "Endpoints para la gestión de pedidos del restaurante"
)
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService service;

    @Operation(
            summary = "Buscar pedido por ID",
            description = "Obtiene un pedido específico mediante su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> findById(@PathVariable Long id) {
        logger.info("Solicitud recibida para buscar pedido por id={}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Listar todos los pedidos",
            description = "Obtiene todos los pedidos registrados en el sistema."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pedidos listados correctamente"
    )
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> findAll() {
        logger.info("Solicitud recibida para listar todos los pedidos");
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Buscar pedidos por cliente",
            description = "Obtiene todos los pedidos asociados a un comensal mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados correctamente"),
            @ApiResponse(responseCode = "400", description = "Cliente no encontrado o solicitud inválida")
    })
    @GetMapping("/by-diner/{dinerId}")
    public ResponseEntity<List<OrderResponseDto>> findByDinerId(@PathVariable Long dinerId) {
        logger.info("Solicitud recibida para buscar pedidos por dinerId={}", dinerId);
        return ResponseEntity.ok(service.findByDinerId(dinerId));
    }

    @Operation(
            summary = "Buscar pedidos por mesa",
            description = "Obtiene todos los pedidos asociados a una mesa mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados correctamente"),
            @ApiResponse(responseCode = "400", description = "Mesa no encontrada o solicitud inválida")
    })
    @GetMapping("/by-table/{tableId}")
    public ResponseEntity<List<OrderResponseDto>> findByTableId(@PathVariable Long tableId) {
        logger.info("Solicitud recibida para buscar pedidos por tableId={}", tableId);
        return ResponseEntity.ok(service.findByTableId(tableId));
    }

    @Operation(
            summary = "Buscar pedidos por garzón",
            description = "Obtiene todos los pedidos asociados a un garzón mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados correctamente"),
            @ApiResponse(responseCode = "400", description = "Garzón no encontrado o solicitud inválida")
    })
    @GetMapping("/by-waiter/{waiterId}")
    public ResponseEntity<List<OrderResponseDto>> findByWaiterId(@PathVariable Long waiterId) {
        logger.info("Solicitud recibida para buscar pedidos por waiterId={}", waiterId);
        return ResponseEntity.ok(service.findByWaiterId(waiterId));
    }

    @Operation(
            summary = "Buscar pedidos por estado",
            description = "Obtiene todos los pedidos filtrados por estado."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pedidos filtrados correctamente"
    )
    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<OrderResponseDto>> findByStatus(@PathVariable String status) {
        logger.info("Solicitud recibida para buscar pedidos por status={}", status);
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @Operation(
            summary = "Buscar pedidos por fecha",
            description = "Obtiene todos los pedidos registrados en una fecha específica."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pedidos filtrados por fecha correctamente"
    )
    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<OrderResponseDto>> findByDate(@PathVariable String date) {
        logger.info("Solicitud recibida para buscar pedidos por date={}", date);
        return ResponseEntity.ok(service.findByDate(date));
    }

    @Operation(
            summary = "Crear pedido",
            description = "Crea un nuevo pedido validando cliente, mesa, garzón y productos del menú."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o recurso relacionado no encontrado")
    })
    @PostMapping
    public ResponseEntity<OrderResponseDto> create(@Valid @RequestBody OrderRequestDto dto) {
        logger.info("Solicitud recibida para crear pedido. dinerId={}, tableId={}, waiterId={}",
                dto.getDinerId(),
                dto.getTableId(),
                dto.getWaiterId());

        OrderResponseDto response = service.create(dto);

        if (response == null) {
            logger.warn("No se pudo crear pedido desde controller");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Pedido creado correctamente desde controller. orderId={}", response.getId());

        return ResponseEntity.created(null).body(response);
    }

    @Operation(
            summary = "Actualizar estado de pedido",
            description = "Actualiza el estado de un pedido existente mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Pedido no encontrado o estado inválido")
    })
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<OrderResponseDto> updateStatus(
            @PathVariable Long id,
            @PathVariable String status
    ) {
        logger.info("Solicitud recibida para actualizar estado de pedido. orderId={}, nuevoStatus={}", id, status);

        OrderResponseDto response = service.updateStatus(id, status);

        if (response == null) {
            logger.warn("No se pudo actualizar estado de pedido desde controller. orderId={}", id);
            return ResponseEntity.badRequest().build();
        }

        logger.info("Estado de pedido actualizado correctamente desde controller. orderId={}, status={}",
                response.getId(),
                response.getStatus());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Eliminar pedido",
            description = "Elimina un pedido existente mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        logger.info("Solicitud recibida para eliminar pedido. orderId={}", id);
        return ResponseEntity.ok(service.delete(id));
    }
}