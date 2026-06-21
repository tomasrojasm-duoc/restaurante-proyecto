package com.menu.tbrm.restaurante.menu.service;

import com.menu.tbrm.restaurante.menu.dto.MenuItemRequestDto;
import com.menu.tbrm.restaurante.menu.dto.MenuItemResponseDto;
import java.util.List;

public interface MenuItemService {

    List<MenuItemResponseDto> findAll();
    MenuItemResponseDto findById(Long id);
    MenuItemResponseDto findByName(String name);
    List<MenuItemResponseDto> findByCategory(String category);
    List<MenuItemResponseDto> findByStatus(String status);
    List<MenuItemResponseDto> findByCategoryAndStatus(String category, String status);
    List<MenuItemResponseDto> findByMaxPrice(Integer price);
    MenuItemResponseDto create(MenuItemRequestDto dto);
    MenuItemResponseDto update(MenuItemRequestDto dto);
    boolean delete(Long id);
}