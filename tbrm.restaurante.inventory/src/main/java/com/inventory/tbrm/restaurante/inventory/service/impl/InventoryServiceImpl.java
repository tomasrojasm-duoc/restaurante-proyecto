package com.inventory.tbrm.restaurante.inventory.service.impl;

import com.inventory.tbrm.restaurante.inventory.dto.InventoryRequestDto;
import com.inventory.tbrm.restaurante.inventory.dto.InventoryResponseDto;
import com.inventory.tbrm.restaurante.inventory.model.Inventory;
import com.inventory.tbrm.restaurante.inventory.repository.InventoryRepository;
import com.inventory.tbrm.restaurante.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final InventoryRepository repository;

    private InventoryResponseDto toDto(Inventory entity) {
        return new InventoryResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getStock(),
                entity.getMinimumStock(),
                entity.getUnit(),
                entity.getStatus()
        );
    }

    private Inventory toEntity(InventoryRequestDto dto) {
        return new Inventory(
                dto.getId(),
                dto.getName(),
                dto.getStock(),
                dto.getMinimumStock(),
                dto.getUnit(),
                dto.getStatus()
        );
    }

    private String calculateStatus(Integer stock, Integer minimumStock) {
        logger.info("Calculando estado de inventario. stock={}, minimumStock={}", stock, minimumStock);

        if (stock == 0) {
            logger.warn("Stock agotado detectado. stock={}", stock);
            return "OUT_OF_STOCK";
        }

        if (stock <= minimumStock) {
            logger.warn("Stock bajo detectado. stock={}, minimumStock={}", stock, minimumStock);
            return "LOW_STOCK";
        }

        return "AVAILABLE";
    }

    @Override
    public List<InventoryResponseDto> findAll() {
        logger.info("Buscando todos los productos de inventario");

        List<InventoryResponseDto> items = repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Productos de inventario encontrados. totalItems={}", items.size());

        return items;
    }

    @Override
    public InventoryResponseDto findById(Long id) {
        logger.info("Buscando producto de inventario por id={}", id);

        InventoryResponseDto response = repository.findById(id)
                .map(this::toDto)
                .orElse(null);

        if (response == null) {
            logger.warn("Producto de inventario no encontrado. inventoryId={}", id);
            return null;
        }

        logger.info("Producto de inventario encontrado. inventoryId={}, name={}, stock={}, status={}",
                response.getId(),
                response.getName(),
                response.getStock(),
                response.getStatus());

        return response;
    }

    @Override
    public InventoryResponseDto findByName(String name) {
        logger.info("Buscando producto de inventario por nombre={}", name);

        Inventory item = repository.findByName(name);

        if (item == null) {
            logger.warn("Producto de inventario no encontrado. name={}", name);
            return null;
        }

        logger.info("Producto de inventario encontrado. inventoryId={}, name={}, stock={}, status={}",
                item.getId(),
                item.getName(),
                item.getStock(),
                item.getStatus());

        return toDto(item);
    }

    @Override
    public List<InventoryResponseDto> findByStatus(String status) {
        logger.info("Buscando productos de inventario por status={}", status);

        List<InventoryResponseDto> items = repository.findByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Productos encontrados para status={}. totalItems={}", status, items.size());

        return items;
    }

    @Override
    public List<InventoryResponseDto> findByUnit(String unit) {
        logger.info("Buscando productos de inventario por unidad={}", unit);

        List<InventoryResponseDto> items = repository.findByUnit(unit)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Productos encontrados para unit={}. totalItems={}", unit, items.size());

        return items;
    }

    @Override
    public List<InventoryResponseDto> findByStockLessThanEqual(Integer stock) {
        logger.info("Buscando productos con stock menor o igual a {}", stock);

        List<InventoryResponseDto> items = repository.findByStockLessThanEqual(stock)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Productos encontrados con stock <= {}. totalItems={}", stock, items.size());

        return items;
    }

    @Override
    public List<InventoryResponseDto> findLowStock() {
        logger.info("Buscando productos con stock bajo");

        List<InventoryResponseDto> items = repository.findAll()
                .stream()
                .filter(item -> item.getStock() <= item.getMinimumStock())
                .map(this::toDto)
                .toList();

        logger.warn("Productos con stock bajo encontrados. totalItems={}", items.size());

        return items;
    }

    @Override
    public InventoryResponseDto create(InventoryRequestDto dto) {
        logger.info("Iniciando creación de producto de inventario. name={}, stock={}, minimumStock={}, unit={}",
                dto.getName(),
                dto.getStock(),
                dto.getMinimumStock(),
                dto.getUnit());

        try {
            if (repository.findByName(dto.getName()) != null) {
                logger.warn("No se pudo crear producto de inventario. Ya existe un producto con name={}",
                        dto.getName());
                return null;
            }

            Inventory item = toEntity(dto);
            item.setStatus(calculateStatus(item.getStock(), item.getMinimumStock()));

            Inventory savedItem = repository.save(item);

            logger.info("Producto de inventario creado correctamente. inventoryId={}, name={}, stock={}, status={}",
                    savedItem.getId(),
                    savedItem.getName(),
                    savedItem.getStock(),
                    savedItem.getStatus());

            return toDto(savedItem);

        } catch (Exception ex) {
            logger.error("Error inesperado al crear producto de inventario. name={}. Motivo={}",
                    dto.getName(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public InventoryResponseDto update(InventoryRequestDto dto) {
        logger.info("Iniciando actualización de producto de inventario. inventoryId={}, name={}",
                dto.getId(),
                dto.getName());

        try {
            if (dto.getId() == null) {
                logger.warn("No se pudo actualizar producto de inventario. El id viene nulo");
                return null;
            }

            if (findById(dto.getId()) == null) {
                logger.warn("No se pudo actualizar producto de inventario. Producto no encontrado. inventoryId={}",
                        dto.getId());
                return null;
            }

            Inventory item = toEntity(dto);
            item.setStatus(calculateStatus(item.getStock(), item.getMinimumStock()));

            Inventory savedItem = repository.save(item);

            logger.info("Producto de inventario actualizado correctamente. inventoryId={}, name={}, stock={}, status={}",
                    savedItem.getId(),
                    savedItem.getName(),
                    savedItem.getStock(),
                    savedItem.getStatus());

            return toDto(savedItem);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar producto de inventario. inventoryId={}, name={}. Motivo={}",
                    dto.getId(),
                    dto.getName(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public InventoryResponseDto updateStock(Long id, Integer stock) {
        logger.info("Actualizando stock de producto de inventario. inventoryId={}, nuevoStock={}", id, stock);

        try {
            Inventory item = repository.findById(id).orElse(null);

            if (item == null) {
                logger.warn("No se pudo actualizar stock. Producto de inventario no encontrado. inventoryId={}", id);
                return null;
            }

            Integer previousStock = item.getStock();

            item.setStock(stock);
            item.setStatus(calculateStatus(item.getStock(), item.getMinimumStock()));

            Inventory savedItem = repository.save(item);

            logger.info("Stock actualizado correctamente. inventoryId={}, stockAnterior={}, nuevoStock={}, status={}",
                    savedItem.getId(),
                    previousStock,
                    savedItem.getStock(),
                    savedItem.getStatus());

            return toDto(savedItem);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar stock. inventoryId={}, nuevoStock={}. Motivo={}",
                    id,
                    stock,
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Intentando eliminar producto de inventario. inventoryId={}", id);

        if (findById(id) == null) {
            logger.warn("No se pudo eliminar producto de inventario. Producto no encontrado. inventoryId={}", id);
            return false;
        }

        repository.deleteById(id);

        logger.info("Producto de inventario eliminado correctamente. inventoryId={}", id);

        return true;
    }
}