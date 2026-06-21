package com.menu.tbrm.restaurante.menu.service.impl;

import com.menu.tbrm.restaurante.menu.dto.MenuItemRequestDto;
import com.menu.tbrm.restaurante.menu.dto.MenuItemResponseDto;
import com.menu.tbrm.restaurante.menu.model.MenuItem;
import com.menu.tbrm.restaurante.menu.repository.MenuItemRepository;
import com.menu.tbrm.restaurante.menu.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemServiceImpl.class);

    private final MenuItemRepository repository;

    private MenuItemResponseDto toDto(MenuItem entity) {
        return new MenuItemResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getPrice(),
                entity.getStatus()
        );
    }

    private MenuItem toEntity(MenuItemRequestDto dto) {
        return new MenuItem(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getCategory(),
                dto.getPrice(),
                dto.getStatus()
        );
    }

    @Override
    public List<MenuItemResponseDto> findAll() {
        logger.info("Buscando todos los productos del menú");

        List<MenuItemResponseDto> items = repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Productos del menú encontrados. totalItems={}", items.size());

        return items;
    }

    @Override
    public MenuItemResponseDto findById(Long id) {
        logger.info("Buscando producto del menú por id={}", id);

        MenuItemResponseDto response = repository.findById(id)
                .map(this::toDto)
                .orElse(null);

        if (response == null) {
            logger.warn("Producto del menú no encontrado. menuItemId={}", id);
            return null;
        }

        logger.info("Producto del menú encontrado. menuItemId={}, name={}, category={}, price={}, status={}",
                response.getId(),
                response.getName(),
                response.getCategory(),
                response.getPrice(),
                response.getStatus());

        return response;
    }

    @Override
    public MenuItemResponseDto findByName(String name) {
        logger.info("Buscando producto del menú por nombre={}", name);

        MenuItem item = repository.findByName(name);

        if (item == null) {
            logger.warn("Producto del menú no encontrado. name={}", name);
            return null;
        }

        logger.info("Producto del menú encontrado. menuItemId={}, name={}, category={}, price={}, status={}",
                item.getId(),
                item.getName(),
                item.getCategory(),
                item.getPrice(),
                item.getStatus());

        return toDto(item);
    }

    @Override
    public List<MenuItemResponseDto> findByCategory(String category) {
        logger.info("Buscando productos del menú por categoría={}", category);

        List<MenuItemResponseDto> items = repository.findByCategory(category)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Productos encontrados para category={}. totalItems={}", category, items.size());

        return items;
    }

    @Override
    public List<MenuItemResponseDto> findByStatus(String status) {
        logger.info("Buscando productos del menú por status={}", status);

        List<MenuItemResponseDto> items = repository.findByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Productos encontrados para status={}. totalItems={}", status, items.size());

        return items;
    }

    @Override
    public List<MenuItemResponseDto> findByCategoryAndStatus(String category, String status) {
        logger.info("Buscando productos del menú por category={} y status={}", category, status);

        List<MenuItemResponseDto> items = repository.findByCategoryAndStatus(category, status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Productos encontrados para category={} y status={}. totalItems={}",
                category,
                status,
                items.size());

        return items;
    }

    @Override
    public List<MenuItemResponseDto> findByMaxPrice(Integer price) {
        logger.info("Buscando productos del menú con precio menor o igual a {}", price);

        List<MenuItemResponseDto> items = repository.findByPriceLessThanEqual(price)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Productos encontrados con precio <= {}. totalItems={}", price, items.size());

        return items;
    }

    @Override
    public MenuItemResponseDto create(MenuItemRequestDto dto) {
        logger.info("Iniciando creación de producto del menú. name={}, category={}, price={}, status={}",
                dto.getName(),
                dto.getCategory(),
                dto.getPrice(),
                dto.getStatus());

        try {
            if (repository.findByName(dto.getName()) != null) {
                logger.warn("No se pudo crear producto del menú. Ya existe un producto con name={}",
                        dto.getName());
                return null;
            }

            MenuItem savedItem = repository.save(toEntity(dto));

            logger.info("Producto del menú creado correctamente. menuItemId={}, name={}, category={}, price={}, status={}",
                    savedItem.getId(),
                    savedItem.getName(),
                    savedItem.getCategory(),
                    savedItem.getPrice(),
                    savedItem.getStatus());

            return toDto(savedItem);

        } catch (Exception ex) {
            logger.error("Error inesperado al crear producto del menú. name={}. Motivo={}",
                    dto.getName(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public MenuItemResponseDto update(MenuItemRequestDto dto) {
        logger.info("Iniciando actualización de producto del menú. menuItemId={}, name={}",
                dto.getId(),
                dto.getName());

        try {
            if (dto.getId() == null) {
                logger.warn("No se pudo actualizar producto del menú. El id viene nulo");
                return null;
            }

            if (findById(dto.getId()) == null) {
                logger.warn("No se pudo actualizar producto del menú. Producto no encontrado. menuItemId={}",
                        dto.getId());
                return null;
            }

            MenuItem savedItem = repository.save(toEntity(dto));

            logger.info("Producto del menú actualizado correctamente. menuItemId={}, name={}, category={}, price={}, status={}",
                    savedItem.getId(),
                    savedItem.getName(),
                    savedItem.getCategory(),
                    savedItem.getPrice(),
                    savedItem.getStatus());

            return toDto(savedItem);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar producto del menú. menuItemId={}, name={}. Motivo={}",
                    dto.getId(),
                    dto.getName(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Intentando eliminar producto del menú. menuItemId={}", id);

        if (findById(id) == null) {
            logger.warn("No se pudo eliminar producto del menú. Producto no encontrado. menuItemId={}", id);
            return false;
        }

        repository.deleteById(id);

        logger.info("Producto del menú eliminado correctamente. menuItemId={}", id);

        return true;
    }
}