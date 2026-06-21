package com.reservation.tbrm.restaurante.reservation.service.impl;

import com.reservation.tbrm.restaurante.reservation.dto.ReservationRequestDto;
import com.reservation.tbrm.restaurante.reservation.dto.ReservationResponseDto;
import com.reservation.tbrm.restaurante.reservation.model.Reservation;
import com.reservation.tbrm.restaurante.reservation.repository.ReservationRepository;
import com.reservation.tbrm.restaurante.reservation.service.ReservationService;
import com.reservation.tbrm.restaurante.reservation.service.apis.DinerClient;
import com.reservation.tbrm.restaurante.reservation.service.apis.TableClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final DinerClient dinerClient;
    private final TableClient tableClient;

    private ReservationResponseDto toDto(Reservation entity) {
        return new ReservationResponseDto(
                entity.getId(),
                entity.getDinerId(),
                entity.getTableId(),
                entity.getDate()
        );
    }

    private Reservation toEntity(ReservationResponseDto dto) {
        return new Reservation(
                dto.getId(),
                dto.getDinerId(),
                dto.getTableId(),
                dto.getDate()
        );
    }

    private Reservation toEntity(ReservationRequestDto dto) {
        return new Reservation(
                dto.getId(),
                dto.getDinerId(),
                dto.getTableId(),
                dto.getDate()
        );
    }

    @Override
    public List<ReservationResponseDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public ReservationResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<ReservationResponseDto> findByTableId(Long tableId) {
        if (tableClient.findById(tableId) == null) {
            log.warn("Table not found with id {}", tableId);
            return null;
        }

        return repository.findByTableId(tableId).stream().map(this::toDto).toList();
    }

    @Override
    public List<ReservationResponseDto> findByTableIdAndDinerId(Long tableId, Long dinerId) {
        return repository.findByTableIdAndDinerId(tableId, dinerId).stream().map(this::toDto).toList();
    }

    @Override
    public List<ReservationResponseDto> findByDinerId(Long dinerId) {
        if (dinerClient.findById(dinerId) == null) {
            log.warn("Diner not found with id {}", dinerId);
            return null;
        }

        return repository.findByDinerId(dinerId).stream().map(this::toDto).toList();
    }

    @Override
    public ReservationResponseDto create(ReservationRequestDto dto) {
        if (dinerClient.findById(dto.getDinerId()) == null) {
            log.warn("Diner not found with id {}", dto.getDinerId());
            return null;
        }

        if (tableClient.findById(dto.getTableId()) == null) {
            log.warn("Table not found with id {}", dto.getTableId());
            return null;
        }

        return this.toDto(repository.save(toEntity(dto)));
    }

    @Override
    public ReservationResponseDto update(ReservationRequestDto dto) {
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