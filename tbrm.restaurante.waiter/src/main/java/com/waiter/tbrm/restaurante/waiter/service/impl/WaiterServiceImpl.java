package com.waiter.tbrm.restaurante.waiter.service.impl;

import com.waiter.tbrm.restaurante.waiter.dto.WaiterRequestDto;
import com.waiter.tbrm.restaurante.waiter.dto.WaiterResponseDto;
import com.waiter.tbrm.restaurante.waiter.model.Waiter;
import com.waiter.tbrm.restaurante.waiter.repository.WaiterRepository;
import com.waiter.tbrm.restaurante.waiter.service.WaiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class WaiterServiceImpl implements WaiterService {

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
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public WaiterResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public WaiterResponseDto findByRun(String run) {
        Waiter waiter = repository.findByRun(run);

        if (waiter == null) {
            log.warn("Waiter not found with run {}", run);
            return null;
        }

        return toDto(waiter);
    }

    @Override
    public List<WaiterResponseDto> findByShift(String shift) {
        return repository.findByShift(shift).stream().map(this::toDto).toList();
    }

    @Override
    public List<WaiterResponseDto> findByStatus(String status) {
        return repository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public List<WaiterResponseDto> findByShiftAndStatus(String shift, String status) {
        return repository.findByShiftAndStatus(shift, status)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public WaiterResponseDto create(WaiterRequestDto dto) {
        if (repository.findByRun(dto.getRun()) != null) {
            log.warn("Waiter already exists with run {}", dto.getRun());
            return null;
        }

        if (repository.findByEmail(dto.getEmail()) != null) {
            log.warn("Waiter already exists with email {}", dto.getEmail());
            return null;
        }

        return this.toDto(repository.save(toEntity(dto)));
    }

    @Override
    public WaiterResponseDto update(WaiterRequestDto dto) {
        if (dto.getId() == null) {
            return null;
        }

        if (findById(dto.getId()) == null) {
            return null;
        }

        return this.toDto(repository.save(toEntity(dto)));
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