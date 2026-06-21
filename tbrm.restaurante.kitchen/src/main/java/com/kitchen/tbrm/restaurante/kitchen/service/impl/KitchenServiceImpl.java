package com.kitchen.tbrm.restaurante.kitchen.service.impl;

import com.kitchen.tbrm.restaurante.kitchen.dto.KitchenRequestDto;
import com.kitchen.tbrm.restaurante.kitchen.dto.KitchenResponseDto;
import com.kitchen.tbrm.restaurante.kitchen.model.KitchenTicket;
import com.kitchen.tbrm.restaurante.kitchen.repository.KitchenTicketRepository;
import com.kitchen.tbrm.restaurante.kitchen.service.KitchenService;
import com.kitchen.tbrm.restaurante.kitchen.service.apis.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class KitchenServiceImpl implements KitchenService {

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
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public KitchenResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public KitchenResponseDto findByOrderId(Long orderId) {
        if (orderClient.findById(orderId) == null) {
            log.warn("Order not found with id {}", orderId);
            return null;
        }

        KitchenTicket kitchenTicket= repository.findByOrderId(orderId);

        if (kitchenTicket == null) {
            return null;
        }

        return toDto(kitchenTicket);
    }

    @Override
    public List<KitchenResponseDto> findByStatus(String status) {
        return repository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public KitchenResponseDto create(KitchenRequestDto dto) {
        if (orderClient.findById(dto.getOrderId()) == null) {
            log.warn("Order not found with id {}", dto.getOrderId());
            return null;
        }

        if (repository.findByOrderId(dto.getOrderId()) != null) {
            log.warn("Kitchen order already exists with order id {}", dto.getOrderId());
            return null;
        }

        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public KitchenResponseDto update(KitchenRequestDto dto) {
        if (dto.getId() == null) {
            return null;
        }

        if (findById(dto.getId()) == null) {
            return null;
        }

        if (orderClient.findById(dto.getOrderId()) == null) {
            log.warn("Order not found with id {}", dto.getOrderId());
            return null;
        }

        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public KitchenResponseDto updateStatus(Long id, String status) {
        KitchenTicket kitchenOrder = repository.findById(id).orElse(null);

        if (kitchenOrder == null) {
            return null;
        }

        kitchenOrder.setStatus(status);

        return toDto(repository.save(kitchenOrder));
    }

    @Override
    public boolean delete(Long id) {
        if (findById(id) == null) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}