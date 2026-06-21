package com.order.tbrm.restaurante.order.service.apis;

import com.order.tbrm.restaurante.order.dto.DinerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "diner-service", url = "http://localhost:8081/api/v1/diners")
public interface DinerClient {

    @GetMapping("/{id}")
    DinerResponseDto findById(@PathVariable Long id);
}