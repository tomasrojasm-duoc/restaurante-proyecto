package com.table.tbrm.restaurante.table.service.impl;

import com.table.tbrm.restaurante.table.dto.TableRequestDto;
import com.table.tbrm.restaurante.table.dto.TableResponseDto;
import com.table.tbrm.restaurante.table.model.TableRestaurant;
import com.table.tbrm.restaurante.table.repository.TableRepository;
import com.table.tbrm.restaurante.table.service.TableService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private static final Logger logger = LoggerFactory.getLogger(TableServiceImpl.class);

    private final TableRepository repository;

    private TableResponseDto toDto(TableRestaurant entity) {
        return new TableResponseDto(
                entity.getId(),
                entity.getTableNumber(),
                entity.getCapacity(),
                entity.getStatus()
        );
    }

    private TableRestaurant toEntity(TableRequestDto dto) {
        return new TableRestaurant(
                dto.getId(),
                dto.getTableNumber(),
                dto.getCapacity(),
                dto.getStatus()
        );
    }

    @Override
    public List<TableResponseDto> findAll() {
        logger.info("Buscando todas las mesas");

        List<TableResponseDto> tables = repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Mesas encontradas. totalTables={}", tables.size());

        return tables;
    }

    @Override
    public TableResponseDto findById(Long id) {
        logger.info("Buscando mesa por id={}", id);

        TableResponseDto response = repository.findById(id)
                .map(this::toDto)
                .orElse(null);

        if (response == null) {
            logger.warn("Mesa no encontrada. tableId={}", id);
            return null;
        }

        logger.info("Mesa encontrada. tableId={}, tableNumber={}, capacity={}, status={}",
                response.getId(),
                response.getTableNumber(),
                response.getCapacity(),
                response.getStatus());

        return response;
    }

    @Override
    public List<TableResponseDto> findByStatus(String status) {
        logger.info("Buscando mesas por status={}", status);

        List<TableResponseDto> tables = repository.findByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Mesas encontradas para status={}. totalTables={}", status, tables.size());

        return tables;
    }

    @Override
    public List<TableResponseDto> findByCapacity(Integer capacity) {
        logger.info("Buscando mesas con capacidad mayor o igual a {}", capacity);

        List<TableResponseDto> tables = repository.findByCapacityGreaterThanEqual(capacity)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Mesas encontradas con capacidad >= {}. totalTables={}", capacity, tables.size());

        return tables;
    }

    @Override
    public TableResponseDto findByTableNumber(Integer tableNumber) {
        logger.info("Buscando mesa por número={}", tableNumber);

        TableRestaurant table = repository.findByTableNumber(tableNumber);

        if (table == null) {
            logger.warn("Mesa no encontrada. tableNumber={}", tableNumber);
            return null;
        }

        logger.info("Mesa encontrada. tableId={}, tableNumber={}, capacity={}, status={}",
                table.getId(),
                table.getTableNumber(),
                table.getCapacity(),
                table.getStatus());

        return toDto(table);
    }

    @Override
    public TableResponseDto create(TableRequestDto dto) {
        logger.info("Iniciando creación de mesa. tableNumber={}, capacity={}, status={}",
                dto.getTableNumber(),
                dto.getCapacity(),
                dto.getStatus());

        try {
            if (repository.findByTableNumber(dto.getTableNumber()) != null) {
                logger.warn("No se pudo crear mesa. Ya existe una mesa con tableNumber={}",
                        dto.getTableNumber());
                return null;
            }

            TableRestaurant savedTable = repository.save(toEntity(dto));

            logger.info("Mesa creada correctamente. tableId={}, tableNumber={}, capacity={}, status={}",
                    savedTable.getId(),
                    savedTable.getTableNumber(),
                    savedTable.getCapacity(),
                    savedTable.getStatus());

            return toDto(savedTable);

        } catch (Exception ex) {
            logger.error("Error inesperado al crear mesa. tableNumber={}. Motivo={}",
                    dto.getTableNumber(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public TableResponseDto update(TableRequestDto dto) {
        logger.info("Iniciando actualización de mesa. tableId={}, tableNumber={}",
                dto.getId(),
                dto.getTableNumber());

        try {
            if (dto.getId() == null) {
                logger.warn("No se pudo actualizar mesa. El id viene nulo");
                return null;
            }

            if (findById(dto.getId()) == null) {
                logger.warn("No se pudo actualizar mesa. Mesa no encontrada. tableId={}", dto.getId());
                return null;
            }

            TableRestaurant savedTable = repository.save(toEntity(dto));

            logger.info("Mesa actualizada correctamente. tableId={}, tableNumber={}, capacity={}, status={}",
                    savedTable.getId(),
                    savedTable.getTableNumber(),
                    savedTable.getCapacity(),
                    savedTable.getStatus());

            return toDto(savedTable);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar mesa. tableId={}, tableNumber={}. Motivo={}",
                    dto.getId(),
                    dto.getTableNumber(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Intentando eliminar mesa. tableId={}", id);

        if (findById(id) == null) {
            logger.warn("No se pudo eliminar mesa. Mesa no encontrada. tableId={}", id);
            return false;
        }

        repository.deleteById(id);

        logger.info("Mesa eliminada correctamente. tableId={}", id);

        return true;
    }
}