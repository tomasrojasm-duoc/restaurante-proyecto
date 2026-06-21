package com.kitchen.tbrm.restaurante.kitchen.service.apis;

import com.kitchen.tbrm.restaurante.kitchen.dto.KitchenResponseDto;
import com.kitchen.tbrm.restaurante.kitchen.dto.KitchenRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", url = "http://localhost:8087/api/v1/orders")
public interface OrderClient {

    @GetMapping("/{id}")
    KitchenResponseDto findById(@PathVariable Long id);
}