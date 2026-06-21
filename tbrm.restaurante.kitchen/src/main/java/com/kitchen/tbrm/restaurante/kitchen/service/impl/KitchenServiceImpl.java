package com.kitchen.tbrm.restaurante.kitchen.service.impl;

import com.kitchen.tbrm.restaurante.kitchen.dto.KitchenRequestDto;
import com.kitchen.tbrm.restaurante.kitchen.dto.KitchenResponseDto;
import com.kitchen.tbrm.restaurante.kitchen.model.KitchenTicket;
import com.kitchen.tbrm.restaurante.kitchen.repository.KitchenTicketRepository;
import com.kitchen.tbrm.restaurante.kitchen.service.KitchenService;
import com.kitchen.tbrm.restaurante.kitchen.service.apis.OrderClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private static final Logger logger = LoggerFactory.getLogger(KitchenServiceImpl.class);

    private final KitchenTicketRepository repository;
    private final OrderClient orderClient;

    private KitchenResponseDto toDto(KitchenTicket entity) {
        return new KitchenResponseDto(
                entity.getId(),
                entity.getOrderId(),
                entity.getStatus(),
                entity.getEstimatedTime(),
                entity.getObservations()
        );
    }

    private KitchenTicket toEntity(KitchenRequestDto dto) {
        return new KitchenTicket(
                dto.getId(),
                dto.getOrderId(),
                dto.getStatus(),
                dto.getEstimatedTime(),
                dto.getObservations()
        );
    }

    @Override
    public List<KitchenResponseDto> findAll() {
        logger.info("Buscando todos los tickets de cocina");

        List<KitchenResponseDto> tickets = repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Tickets de cocina encontrados. totalTickets={}", tickets.size());

        return tickets;
    }

    @Override
    public KitchenResponseDto findById(Long id) {
        logger.info("Buscando ticket de cocina por id={}", id);

        KitchenResponseDto response = repository.findById(id)
                .map(this::toDto)
                .orElse(null);

        if (response == null) {
            logger.warn("Ticket de cocina no encontrado. kitchenTicketId={}", id);
            return null;
        }

        logger.info("Ticket de cocina encontrado. kitchenTicketId={}, orderId={}, status={}",
                response.getId(),
                response.getOrderId(),
                response.getStatus());

        return response;
    }

    @Override
    public KitchenResponseDto findByOrderId(Long orderId) {
        logger.info("Buscando ticket de cocina por orderId={}", orderId);

        logger.info("Validando existencia de pedido en order. orderId={}", orderId);

        if (orderClient.findById(orderId) == null) {
            logger.warn("No se pudo buscar ticket de cocina. Pedido no encontrado. orderId={}", orderId);
            return null;
        }

        KitchenTicket kitchenTicket = repository.findByOrderId(orderId);

        if (kitchenTicket == null) {
            logger.warn("No existe ticket de cocina asociado al pedido. orderId={}", orderId);
            return null;
        }

        logger.info("Ticket de cocina encontrado para orderId={}. kitchenTicketId={}, status={}",
                orderId,
                kitchenTicket.getId(),
                kitchenTicket.getStatus());

        return toDto(kitchenTicket);
    }

    @Override
    public List<KitchenResponseDto> findByStatus(String status) {
        logger.info("Buscando tickets de cocina por status={}", status);

        List<KitchenResponseDto> tickets = repository.findByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Tickets de cocina encontrados para status={}. totalTickets={}", status, tickets.size());

        return tickets;
    }

    @Override
    public KitchenResponseDto create(KitchenRequestDto dto) {
        logger.info("Iniciando creación de ticket de cocina. orderId={}, status={}",
                dto.getOrderId(),
                dto.getStatus());

        try {
            logger.info("Validando existencia de pedido en order. orderId={}", dto.getOrderId());

            if (orderClient.findById(dto.getOrderId()) == null) {
                logger.warn("No se pudo crear ticket de cocina. Pedido no encontrado. orderId={}",
                        dto.getOrderId());
                return null;
            }

            logger.info("Pedido validado correctamente. orderId={}", dto.getOrderId());

            if (repository.findByOrderId(dto.getOrderId()) != null) {
                logger.warn("No se pudo crear ticket de cocina. Ya existe ticket asociado al orderId={}",
                        dto.getOrderId());
                return null;
            }

            KitchenTicket savedTicket = repository.save(toEntity(dto));

            logger.info("Ticket de cocina creado correctamente. kitchenTicketId={}, orderId={}, status={}",
                    savedTicket.getId(),
                    savedTicket.getOrderId(),
                    savedTicket.getStatus());

            return toDto(savedTicket);

        } catch (Exception ex) {
            logger.error("Error inesperado al crear ticket de cocina. orderId={}. Motivo={}",
                    dto.getOrderId(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public KitchenResponseDto update(KitchenRequestDto dto) {
        logger.info("Iniciando actualización de ticket de cocina. kitchenTicketId={}, orderId={}",
                dto.getId(),
                dto.getOrderId());

        try {
            if (dto.getId() == null) {
                logger.warn("No se pudo actualizar ticket de cocina. El id viene nulo");
                return null;
            }

            if (findById(dto.getId()) == null) {
                logger.warn("No se pudo actualizar ticket de cocina. Ticket no encontrado. kitchenTicketId={}",
                        dto.getId());
                return null;
            }

            logger.info("Validando existencia de pedido en order. orderId={}", dto.getOrderId());

            if (orderClient.findById(dto.getOrderId()) == null) {
                logger.warn("No se pudo actualizar ticket de cocina. Pedido no encontrado. orderId={}",
                        dto.getOrderId());
                return null;
            }

            KitchenTicket savedTicket = repository.save(toEntity(dto));

            logger.info("Ticket de cocina actualizado correctamente. kitchenTicketId={}, orderId={}, status={}",
                    savedTicket.getId(),
                    savedTicket.getOrderId(),
                    savedTicket.getStatus());

            return toDto(savedTicket);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar ticket de cocina. kitchenTicketId={}, orderId={}. Motivo={}",
                    dto.getId(),
                    dto.getOrderId(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public KitchenResponseDto updateStatus(Long id, String status) {
        logger.info("Actualizando estado de ticket de cocina. kitchenTicketId={}, nuevoStatus={}", id, status);

        KitchenTicket kitchenOrder = repository.findById(id).orElse(null);

        if (kitchenOrder == null) {
            logger.warn("No se pudo actualizar estado. Ticket de cocina no encontrado. kitchenTicketId={}", id);
            return null;
        }

        String previousStatus = kitchenOrder.getStatus();

        kitchenOrder.setStatus(status);

        KitchenTicket savedTicket = repository.save(kitchenOrder);

        logger.info("Estado de ticket de cocina actualizado correctamente. kitchenTicketId={}, estadoAnterior={}, nuevoEstado={}",
                savedTicket.getId(),
                previousStatus,
                savedTicket.getStatus());

        return toDto(savedTicket);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Intentando eliminar ticket de cocina. kitchenTicketId={}", id);

        if (findById(id) == null) {
            logger.warn("No se pudo eliminar ticket de cocina. Ticket no encontrado. kitchenTicketId={}", id);
            return false;
        }

        repository.deleteById(id);

        logger.info("Ticket de cocina eliminado correctamente. kitchenTicketId={}", id);

        return true;
    }
}