package com.order.tbrm.restaurante.order.service.impl;

import com.order.tbrm.restaurante.order.dto.*;
import com.order.tbrm.restaurante.order.model.OrderDetail;
import com.order.tbrm.restaurante.order.model.Order;
import com.order.tbrm.restaurante.order.repository.RestaurantOrderRepository;
import com.order.tbrm.restaurante.order.service.OrderService;
import com.order.tbrm.restaurante.order.service.apis.DinerClient;
import com.order.tbrm.restaurante.order.service.apis.MenuClient;
import com.order.tbrm.restaurante.order.service.apis.TableClient;
import com.order.tbrm.restaurante.order.service.apis.WaiterClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final RestaurantOrderRepository repository;
    private final DinerClient dinerClient;
    private final TableClient tableClient;
    private final WaiterClient waiterClient;
    private final MenuClient menuClient;

    private OrderDetailResponseDto detailToDto(OrderDetail detail) {
        return new OrderDetailResponseDto(
                detail.getId(),
                detail.getMenuItemId(),
                detail.getQuantity(),
                detail.getUnitPrice(),
                detail.getSubtotal()
        );
    }

    private OrderResponseDto toDto(Order entity) {
        List<OrderDetailResponseDto> detailDtos = entity.getDetails()
                .stream()
                .map(this::detailToDto)
                .toList();

        return new OrderResponseDto(
                entity.getId(),
                entity.getDinerId(),
                entity.getTableId(),
                entity.getWaiterId(),
                entity.getDate(),
                entity.getStatus(),
                entity.getTotal(),
                detailDtos
        );
    }

    @Override
    public List<OrderResponseDto> findAll() {
        logger.info("Buscando todos los pedidos en base de datos");

        List<OrderResponseDto> orders = repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Pedidos encontrados. totalPedidos={}", orders.size());

        return orders;
    }

    @Override
    public OrderResponseDto findById(Long id) {
        logger.info("Buscando pedido por id={}", id);

        OrderResponseDto response = repository.findById(id)
                .map(this::toDto)
                .orElse(null);

        if (response == null) {
            logger.warn("Pedido no encontrado. orderId={}", id);
            return null;
        }

        logger.info("Pedido encontrado. orderId={}, status={}, total={}",
                response.getId(),
                response.getStatus(),
                response.getTotal());

        return response;
    }

    @Override
    public List<OrderResponseDto> findByDinerId(Long dinerId) {
        logger.info("Buscando pedidos por dinerId={}", dinerId);

        if (dinerClient.findById(dinerId) == null) {
            logger.warn("No se encontraron pedidos porque el diner no existe. dinerId={}", dinerId);
            return null;
        }

        List<OrderResponseDto> orders = repository.findByDinerId(dinerId)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Pedidos encontrados para dinerId={}. totalPedidos={}", dinerId, orders.size());

        return orders;
    }

    @Override
    public List<OrderResponseDto> findByTableId(Long tableId) {
        logger.info("Buscando pedidos por tableId={}", tableId);

        if (tableClient.findById(tableId) == null) {
            logger.warn("No se encontraron pedidos porque la mesa no existe. tableId={}", tableId);
            return null;
        }

        List<OrderResponseDto> orders = repository.findByTableId(tableId)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Pedidos encontrados para tableId={}. totalPedidos={}", tableId, orders.size());

        return orders;
    }

    @Override
    public List<OrderResponseDto> findByWaiterId(Long waiterId) {
        logger.info("Buscando pedidos por waiterId={}", waiterId);

        if (waiterClient.findById(waiterId) == null) {
            logger.warn("No se encontraron pedidos porque el waiter no existe. waiterId={}", waiterId);
            return null;
        }

        List<OrderResponseDto> orders = repository.findByWaiterId(waiterId)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Pedidos encontrados para waiterId={}. totalPedidos={}", waiterId, orders.size());

        return orders;
    }

    @Override
    public List<OrderResponseDto> findByStatus(String status) {
        logger.info("Buscando pedidos por status={}", status);

        List<OrderResponseDto> orders = repository.findByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Pedidos encontrados para status={}. totalPedidos={}", status, orders.size());

        return orders;
    }

    @Override
    public List<OrderResponseDto> findByDate(String date) {
        logger.info("Buscando pedidos por date={}", date);

        List<OrderResponseDto> orders = repository.findByDate(date)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Pedidos encontrados para date={}. totalPedidos={}", date, orders.size());

        return orders;
    }

    @Override
    public OrderResponseDto create(OrderRequestDto dto) {
        logger.info("Iniciando creación de pedido. dinerId={}, tableId={}, waiterId={}",
                dto.getDinerId(),
                dto.getTableId(),
                dto.getWaiterId());

        try {
            logger.info("Validando diner. dinerId={}", dto.getDinerId());

            if (dinerClient.findById(dto.getDinerId()) == null) {
                logger.warn("No se pudo crear pedido. Diner no encontrado. dinerId={}", dto.getDinerId());
                return null;
            }

            logger.info("Diner validado correctamente. dinerId={}", dto.getDinerId());

            logger.info("Validando table. tableId={}", dto.getTableId());

            if (tableClient.findById(dto.getTableId()) == null) {
                logger.warn("No se pudo crear pedido. Mesa no encontrada. tableId={}", dto.getTableId());
                return null;
            }

            logger.info("Mesa validada correctamente. tableId={}", dto.getTableId());

            logger.info("Validando waiter. waiterId={}", dto.getWaiterId());

            if (waiterClient.findById(dto.getWaiterId()) == null) {
                logger.warn("No se pudo crear pedido. Waiter no encontrado. waiterId={}", dto.getWaiterId());
                return null;
            }

            logger.info("Waiter validado correctamente. waiterId={}", dto.getWaiterId());

            Order order = new Order();
            order.setId(dto.getId());
            order.setDinerId(dto.getDinerId());
            order.setTableId(dto.getTableId());
            order.setWaiterId(dto.getWaiterId());
            order.setDate(dto.getDate());
            order.setStatus(dto.getStatus() == null ? "CREATED" : dto.getStatus());

            logger.info("Entidad Order construida. dinerId={}, tableId={}, waiterId={}, status={}",
                    order.getDinerId(),
                    order.getTableId(),
                    order.getWaiterId(),
                    order.getStatus());

            List<OrderDetail> details = new ArrayList<>();
            Integer total = 0;

            logger.info("Procesando detalles del pedido. cantidadDetalles={}", dto.getDetails().size());

            for (OrderDetailRequestDto detailDto : dto.getDetails()) {
                logger.info("Consultando producto en menu. menuItemId={}, quantity={}",
                        detailDto.getMenuItemId(),
                        detailDto.getQuantity());

                MenuItemResponseDto menuItem = menuClient.findById(detailDto.getMenuItemId());

                if (menuItem == null) {
                    logger.warn("No se pudo crear pedido. Producto de menú no encontrado. menuItemId={}",
                            detailDto.getMenuItemId());
                    return null;
                }

                if (!"AVAILABLE".equalsIgnoreCase(menuItem.getStatus())) {
                    logger.warn("No se pudo crear pedido. Producto no disponible. menuItemId={}, status={}",
                            detailDto.getMenuItemId(),
                            menuItem.getStatus());
                    return null;
                }

                Integer unitPrice = menuItem.getPrice();
                Integer subtotal = unitPrice * detailDto.getQuantity();

                logger.info("Detalle calculado. menuItemId={}, unitPrice={}, quantity={}, subtotal={}",
                        detailDto.getMenuItemId(),
                        unitPrice,
                        detailDto.getQuantity(),
                        subtotal);

                OrderDetail detail = new OrderDetail();
                detail.setMenuItemId(detailDto.getMenuItemId());
                detail.setQuantity(detailDto.getQuantity());
                detail.setUnitPrice(unitPrice);
                detail.setSubtotal(subtotal);
                detail.setOrder(order);

                details.add(detail);
                total += subtotal;
            }

            order.setDetails(details);
            order.setTotal(total);

            logger.info("Total calculado para el pedido. total={}", total);

            Order savedOrder = repository.save(order);

            logger.info("Pedido guardado correctamente. orderId={}, total={}, status={}",
                    savedOrder.getId(),
                    savedOrder.getTotal(),
                    savedOrder.getStatus());

            return toDto(savedOrder);

        } catch (Exception ex) {
            logger.error("Error inesperado al crear pedido. dinerId={}, tableId={}, waiterId={}. Motivo={}",
                    dto.getDinerId(),
                    dto.getTableId(),
                    dto.getWaiterId(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public OrderResponseDto updateStatus(Long id, String status) {
        logger.info("Actualizando estado del pedido. orderId={}, nuevoStatus={}", id, status);

        Order order = repository.findById(id).orElse(null);

        if (order == null) {
            logger.warn("No se pudo actualizar el estado. Pedido no encontrado. orderId={}", id);
            return null;
        }

        String previousStatus = order.getStatus();

        order.setStatus(status);

        Order savedOrder = repository.save(order);

        logger.info("Estado de pedido actualizado correctamente. orderId={}, estadoAnterior={}, nuevoEstado={}",
                savedOrder.getId(),
                previousStatus,
                savedOrder.getStatus());

        return toDto(savedOrder);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Intentando eliminar pedido. orderId={}", id);

        if (findById(id) == null) {
            logger.warn("No se pudo eliminar el pedido. Pedido no encontrado. orderId={}", id);
            return false;
        }

        repository.deleteById(id);

        logger.info("Pedido eliminado correctamente. orderId={}", id);

        return true;
    }
}