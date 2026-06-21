package com.menu.tbrm.restaurante.menu.service.impl;

import com.menu.tbrm.restaurante.menu.dto.MenuItemRequestDto;
import com.menu.tbrm.restaurante.menu.dto.MenuItemResponseDto;
import com.menu.tbrm.restaurante.menu.model.MenuItem;
import com.menu.tbrm.restaurante.menu.repository.MenuItemRepository;
import com.menu.tbrm.restaurante.menu.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MenuItemServiceImpl implements MenuItemService {

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
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public MenuItemResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public MenuItemResponseDto findByName(String name) {
        MenuItem item = repository.findByName(name);

        if (item == null) {
            log.warn("Menu item not found with name {}", name);
            return null;
        }

        return toDto(item);
    }

    @Override
    public List<MenuItemResponseDto> findByCategory(String category) {
        return repository.findByCategory(category).stream().map(this::toDto).toList();
    }

    @Override
    public List<MenuItemResponseDto> findByStatus(String status) {
        return repository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public List<MenuItemResponseDto> findByCategoryAndStatus(String category, String status) {
        return repository.findByCategoryAndStatus(category, status)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<MenuItemResponseDto> findByMaxPrice(Integer price) {
        return repository.findByPriceLessThanEqual(price)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public MenuItemResponseDto create(MenuItemRequestDto dto) {
        if (repository.findByName(dto.getName()) != null) {
            log.warn("Menu item already exists with name {}", dto.getName());
            return null;
        }

        return this.toDto(repository.save(toEntity(dto)));
    }

    @Override
    public MenuItemResponseDto update(MenuItemRequestDto dto) {
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