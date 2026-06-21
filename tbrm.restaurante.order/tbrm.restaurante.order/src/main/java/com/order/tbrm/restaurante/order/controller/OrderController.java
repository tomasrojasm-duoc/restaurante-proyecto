package com.order.tbrm.restaurante.order.controller;

import com.order.tbrm.restaurante.order.dto.OrderRequestDto;
import com.order.tbrm.restaurante.order.dto.OrderResponseDto;
import com.order.tbrm.restaurante.order.service.OrderService;
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
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService service;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> findById(@PathVariable Long id) {
        logger.info("GET /api/v1/orders/{} - Solicitud para buscar pedido por ID", id);

        OrderResponseDto response = service.findById(id);

        logger.info("Pedido encontrado correctamente. orderId={}", id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> findAll() {
        logger.info("GET /api/v1/orders - Solicitud para listar todos los pedidos");

        List<OrderResponseDto> response = service.findAll();

        logger.info("Listado de pedidos obtenido correctamente. totalPedidos={}", response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-diner/{dinerId}")
    public ResponseEntity<List<OrderResponseDto>> findByDinerId(@PathVariable Long dinerId) {
        logger.info("GET /api/v1/orders/by-diner/{} - Solicitud para listar pedidos por dinerId", dinerId);

        List<OrderResponseDto> response = service.findByDinerId(dinerId);

        logger.info("Pedidos encontrados para dinerId={}. totalPedidos={}", dinerId, response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-table/{tableId}")
    public ResponseEntity<List<OrderResponseDto>> findByTableId(@PathVariable Long tableId) {
        logger.info("GET /api/v1/orders/by-table/{} - Solicitud para listar pedidos por tableId", tableId);

        List<OrderResponseDto> response = service.findByTableId(tableId);

        logger.info("Pedidos encontrados para tableId={}. totalPedidos={}", tableId, response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-waiter/{waiterId}")
    public ResponseEntity<List<OrderResponseDto>> findByWaiterId(@PathVariable Long waiterId) {
        logger.info("GET /api/v1/orders/by-waiter/{} - Solicitud para listar pedidos por waiterId", waiterId);

        List<OrderResponseDto> response = service.findByWaiterId(waiterId);

        logger.info("Pedidos encontrados para waiterId={}. totalPedidos={}", waiterId, response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<OrderResponseDto>> findByStatus(@PathVariable String status) {
        logger.info("GET /api/v1/orders/by-status/{} - Solicitud para listar pedidos por estado", status);

        List<OrderResponseDto> response = service.findByStatus(status);

        logger.info("Pedidos encontrados con status={}. totalPedidos={}", status, response.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<OrderResponseDto>> findByDate(@PathVariable String date) {
        logger.info("GET /api/v1/orders/by-date/{} - Solicitud para listar pedidos por fecha", date);

        List<OrderResponseDto> response = service.findByDate(date);

        logger.info("Pedidos encontrados con date={}. totalPedidos={}", date, response.size());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(@Valid @RequestBody OrderRequestDto dto) {
        logger.info("POST /api/v1/orders - Solicitud para crear pedido. dinerId={}, tableId={}, waiterId={}",
                dto.getDinerId(),
                dto.getTableId(),
                dto.getWaiterId());

        OrderResponseDto response = service.create(dto);

        if (response == null) {
            logger.warn("No se pudo crear el pedido. dinerId={}, tableId={}, waiterId={}",
                    dto.getDinerId(),
                    dto.getTableId(),
                    dto.getWaiterId());

            return ResponseEntity.badRequest().build();
        }

        logger.info("Pedido creado correctamente. orderId={}, dinerId={}, tableId={}, waiterId={}",
                response.getId(),
                response.getDinerId(),
                response.getTableId(),
                response.getWaiterId());

        return ResponseEntity.created(null).body(response);
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<OrderResponseDto> updateStatus(
            @PathVariable Long id,
            @PathVariable String status
    ) {
        logger.info("PATCH /api/v1/orders/{}/status/{} - Solicitud para actualizar estado de pedido", id, status);

        OrderResponseDto response = service.updateStatus(id, status);

        if (response == null) {
            logger.warn("No se pudo actualizar el estado del pedido. orderId={}, nuevoStatus={}", id, status);

            return ResponseEntity.badRequest().build();
        }

        logger.info("Estado del pedido actualizado correctamente. orderId={}, nuevoStatus={}",
                response.getId(),
                response.getStatus());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        logger.info("DELETE /api/v1/orders/{} - Solicitud para eliminar pedido", id);

        Boolean deleted = service.delete(id);

        if (Boolean.FALSE.equals(deleted)) {
            logger.warn("No se pudo eliminar el pedido. orderId={}", id);
        } else {
            logger.info("Pedido eliminado correctamente. orderId={}", id);
        }

        return ResponseEntity.ok(deleted);
    }
}