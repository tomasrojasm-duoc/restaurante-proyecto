package com.reservation.tbrm.restaurante.reservation.service.impl;

import com.reservation.tbrm.restaurante.reservation.dto.ReservationRequestDto;
import com.reservation.tbrm.restaurante.reservation.dto.ReservationResponseDto;
import com.reservation.tbrm.restaurante.reservation.model.Reservation;
import com.reservation.tbrm.restaurante.reservation.repository.ReservationRepository;
import com.reservation.tbrm.restaurante.reservation.service.ReservationService;
import com.reservation.tbrm.restaurante.reservation.service.apis.DinerClient;
import com.reservation.tbrm.restaurante.reservation.service.apis.TableClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

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
        logger.info("Buscando todas las reservas");

        List<ReservationResponseDto> reservations = repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Reservas encontradas. totalReservas={}", reservations.size());

        return reservations;
    }

    @Override
    public ReservationResponseDto findById(Long id) {
        logger.info("Buscando reserva por id={}", id);

        ReservationResponseDto response = repository.findById(id)
                .map(this::toDto)
                .orElse(null);

        if (response == null) {
            logger.warn("Reserva no encontrada. reservationId={}", id);
            return null;
        }

        logger.info("Reserva encontrada. reservationId={}, dinerId={}, tableId={}, date={}",
                response.getId(),
                response.getDinerId(),
                response.getTableId(),
                response.getDate());

        return response;
    }

    @Override
    public List<ReservationResponseDto> findByTableId(Long tableId) {
        logger.info("Buscando reservas por tableId={}", tableId);

        logger.info("Validando existencia de mesa en table. tableId={}", tableId);

        if (tableClient.findById(tableId) == null) {
            logger.warn("No se pudieron buscar reservas. Mesa no encontrada. tableId={}", tableId);
            return null;
        }

        List<ReservationResponseDto> reservations = repository.findByTableId(tableId)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Reservas encontradas para tableId={}. totalReservas={}", tableId, reservations.size());

        return reservations;
    }

    @Override
    public List<ReservationResponseDto> findByTableIdAndDinerId(Long tableId, Long dinerId) {
        logger.info("Buscando reservas por tableId={} y dinerId={}", tableId, dinerId);

        List<ReservationResponseDto> reservations = repository.findByTableIdAndDinerId(tableId, dinerId)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Reservas encontradas para tableId={} y dinerId={}. totalReservas={}",
                tableId,
                dinerId,
                reservations.size());

        return reservations;
    }

    @Override
    public List<ReservationResponseDto> findByDinerId(Long dinerId) {
        logger.info("Buscando reservas por dinerId={}", dinerId);

        logger.info("Validando existencia de cliente en diner. dinerId={}", dinerId);

        if (dinerClient.findById(dinerId) == null) {
            logger.warn("No se pudieron buscar reservas. Cliente no encontrado. dinerId={}", dinerId);
            return null;
        }

        List<ReservationResponseDto> reservations = repository.findByDinerId(dinerId)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Reservas encontradas para dinerId={}. totalReservas={}", dinerId, reservations.size());

        return reservations;
    }

    @Override
    public ReservationResponseDto create(ReservationRequestDto dto) {
        logger.info("Iniciando creación de reserva. dinerId={}, tableId={}, date={}",
                dto.getDinerId(),
                dto.getTableId(),
                dto.getDate());

        try {
            logger.info("Validando existencia de cliente en diner. dinerId={}", dto.getDinerId());

            if (dinerClient.findById(dto.getDinerId()) == null) {
                logger.warn("No se pudo crear reserva. Cliente no encontrado. dinerId={}", dto.getDinerId());
                return null;
            }

            logger.info("Cliente validado correctamente. dinerId={}", dto.getDinerId());

            logger.info("Validando existencia de mesa en table. tableId={}", dto.getTableId());

            if (tableClient.findById(dto.getTableId()) == null) {
                logger.warn("No se pudo crear reserva. Mesa no encontrada. tableId={}", dto.getTableId());
                return null;
            }

            logger.info("Mesa validada correctamente. tableId={}", dto.getTableId());

            Reservation savedReservation = repository.save(toEntity(dto));

            logger.info("Reserva creada correctamente. reservationId={}, dinerId={}, tableId={}, date={}",
                    savedReservation.getId(),
                    savedReservation.getDinerId(),
                    savedReservation.getTableId(),
                    savedReservation.getDate());

            return toDto(savedReservation);

        } catch (Exception ex) {
            logger.error("Error inesperado al crear reserva. dinerId={}, tableId={}, date={}. Motivo={}",
                    dto.getDinerId(),
                    dto.getTableId(),
                    dto.getDate(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public ReservationResponseDto update(ReservationRequestDto dto) {
        logger.info("Iniciando actualización de reserva. reservationId={}, dinerId={}, tableId={}, date={}",
                dto.getId(),
                dto.getDinerId(),
                dto.getTableId(),
                dto.getDate());

        try {
            if (dto.getId() == null) {
                logger.warn("No se pudo actualizar reserva. El id viene nulo");
                return null;
            }

            if (findById(dto.getId()) == null) {
                logger.warn("No se pudo actualizar reserva. Reserva no encontrada. reservationId={}", dto.getId());
                return null;
            }

            Reservation savedReservation = repository.save(toEntity(dto));

            logger.info("Reserva actualizada correctamente. reservationId={}, dinerId={}, tableId={}, date={}",
                    savedReservation.getId(),
                    savedReservation.getDinerId(),
                    savedReservation.getTableId(),
                    savedReservation.getDate());

            return toDto(savedReservation);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar reserva. reservationId={}. Motivo={}",
                    dto.getId(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Intentando eliminar reserva. reservationId={}", id);

        if (findById(id) == null) {
            logger.warn("No se pudo eliminar reserva. Reserva no encontrada. reservationId={}", id);
            return false;
        }

        repository.deleteById(id);

        logger.info("Reserva eliminada correctamente. reservationId={}", id);

        return true;
    }
}