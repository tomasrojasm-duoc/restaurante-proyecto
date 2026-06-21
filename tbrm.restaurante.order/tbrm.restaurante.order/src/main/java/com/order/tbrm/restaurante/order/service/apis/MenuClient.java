package com.order.tbrm.restaurante.order.service.apis;

import com.order.tbrm.restaurante.order.dto.MenuItemResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "menu-service", url = "http://localhost:8086/api/v1/menu-items")
public interface MenuClient {

    @GetMapping("/{id}")
    MenuItemResponseDto findById(@PathVariable Long id);
}