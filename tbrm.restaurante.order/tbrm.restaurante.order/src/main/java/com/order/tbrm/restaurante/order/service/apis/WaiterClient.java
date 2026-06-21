package com.order.tbrm.restaurante.order.service.apis;

import com.order.tbrm.restaurante.order.dto.WaiterResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "waiter-service", url = "http://localhost:8085/api/v1/waiters")
public interface WaiterClient {

    @GetMapping("/{id}")
    WaiterResponseDto findById(@PathVariable Long id);
}