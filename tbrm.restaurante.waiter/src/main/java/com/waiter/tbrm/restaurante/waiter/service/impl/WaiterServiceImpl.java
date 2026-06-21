package com.waiter.tbrm.restaurante.waiter.service.impl;

import com.waiter.tbrm.restaurante.waiter.dto.WaiterRequestDto;
import com.waiter.tbrm.restaurante.waiter.dto.WaiterResponseDto;
import com.waiter.tbrm.restaurante.waiter.model.Waiter;
import com.waiter.tbrm.restaurante.waiter.repository.WaiterRepository;
import com.waiter.tbrm.restaurante.waiter.service.WaiterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WaiterServiceImpl implements WaiterService {

    private static final Logger logger = LoggerFactory.getLogger(WaiterServiceImpl.class);

    private final WaiterRepository repository;

    private WaiterResponseDto toDto(Waiter entity) {
        return new WaiterResponseDto(
                entity.getId(),
                entity.getRun(),
                entity.getName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getShift(),
                entity.getStatus()
        );
    }

    private Waiter toEntity(WaiterRequestDto dto) {
        return new Waiter(
                dto.getId(),
                dto.getRun(),
                dto.getName(),
                dto.getLastName(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getShift(),
                dto.getStatus()
        );
    }

    @Override
    public List<WaiterResponseDto> findAll() {
        logger.info("Buscando todos los garzones");

        List<WaiterResponseDto> waiters = repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Garzones encontrados. totalWaiters={}", waiters.size());

        return waiters;
    }

    @Override
    public WaiterResponseDto findById(Long id) {
        logger.info("Buscando garzón por id={}", id);

        WaiterResponseDto response = repository.findById(id)
                .map(this::toDto)
                .orElse(null);

        if (response == null) {
            logger.warn("Garzón no encontrado. waiterId={}", id);
            return null;
        }

        logger.info("Garzón encontrado. waiterId={}, run={}, email={}, shift={}, status={}",
                response.getId(),
                response.getRun(),
                response.getEmail(),
                response.getShift(),
                response.getStatus());

        return response;
    }

    @Override
    public WaiterResponseDto findByRun(String run) {
        logger.info("Buscando garzón por run={}", run);

        Waiter waiter = repository.findByRun(run);

        if (waiter == null) {
            logger.warn("Garzón no encontrado. run={}", run);
            return null;
        }

        logger.info("Garzón encontrado. waiterId={}, run={}, email={}, shift={}, status={}",
                waiter.getId(),
                waiter.getRun(),
                waiter.getEmail(),
                waiter.getShift(),
                waiter.getStatus());

        return toDto(waiter);
    }

    @Override
    public List<WaiterResponseDto> findByShift(String shift) {
        logger.info("Buscando garzones por turno={}", shift);

        List<WaiterResponseDto> waiters = repository.findByShift(shift)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Garzones encontrados para shift={}. totalWaiters={}", shift, waiters.size());

        return waiters;
    }

    @Override
    public List<WaiterResponseDto> findByStatus(String status) {
        logger.info("Buscando garzones por status={}", status);

        List<WaiterResponseDto> waiters = repository.findByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Garzones encontrados para status={}. totalWaiters={}", status, waiters.size());

        return waiters;
    }

    @Override
    public List<WaiterResponseDto> findByShiftAndStatus(String shift, String status) {
        logger.info("Buscando garzones por shift={} y status={}", shift, status);

        List<WaiterResponseDto> waiters = repository.findByShiftAndStatus(shift, status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Garzones encontrados para shift={} y status={}. totalWaiters={}",
                shift,
                status,
                waiters.size());

        return waiters;
    }

    @Override
    public WaiterResponseDto create(WaiterRequestDto dto) {
        logger.info("Iniciando creación de garzón. run={}, email={}, shift={}, status={}",
                dto.getRun(),
                dto.getEmail(),
                dto.getShift(),
                dto.getStatus());

        try {
            if (repository.findByRun(dto.getRun()) != null) {
                logger.warn("No se pudo crear garzón. Ya existe un garzón con run={}", dto.getRun());
                return null;
            }

            if (repository.findByEmail(dto.getEmail()) != null) {
                logger.warn("No se pudo crear garzón. Ya existe un garzón con email={}", dto.getEmail());
                return null;
            }

            Waiter savedWaiter = repository.save(toEntity(dto));

            logger.info("Garzón creado correctamente. waiterId={}, run={}, email={}, shift={}, status={}",
                    savedWaiter.getId(),
                    savedWaiter.getRun(),
                    savedWaiter.getEmail(),
                    savedWaiter.getShift(),
                    savedWaiter.getStatus());

            return toDto(savedWaiter);

        } catch (Exception ex) {
            logger.error("Error inesperado al crear garzón. run={}, email={}. Motivo={}",
                    dto.getRun(),
                    dto.getEmail(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public WaiterResponseDto update(WaiterRequestDto dto) {
        logger.info("Iniciando actualización de garzón. waiterId={}, run={}, email={}",
                dto.getId(),
                dto.getRun(),
                dto.getEmail());

        try {
            if (dto.getId() == null) {
                logger.warn("No se pudo actualizar garzón. El id viene nulo");
                return null;
            }

            if (findById(dto.getId()) == null) {
                logger.warn("No se pudo actualizar garzón. Garzón no encontrado. waiterId={}", dto.getId());
                return null;
            }

            Waiter savedWaiter = repository.save(toEntity(dto));

            logger.info("Garzón actualizado correctamente. waiterId={}, run={}, email={}, shift={}, status={}",
                    savedWaiter.getId(),
                    savedWaiter.getRun(),
                    savedWaiter.getEmail(),
                    savedWaiter.getShift(),
                    savedWaiter.getStatus());

            return toDto(savedWaiter);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar garzón. waiterId={}, run={}, email={}. Motivo={}",
                    dto.getId(),
                    dto.getRun(),
                    dto.getEmail(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Intentando eliminar garzón. waiterId={}", id);

        if (findById(id) == null) {
            logger.warn("No se pudo eliminar garzón. Garzón no encontrado. waiterId={}", id);
            return false;
        }

        repository.deleteById(id);

        logger.info("Garzón eliminado correctamente. waiterId={}", id);

        return true;
    }
}