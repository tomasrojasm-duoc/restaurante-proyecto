package com.reservation.tbrm.restaurante.reservation.service.apis;

import com.reservation.tbrm.restaurante.reservation.dto.DinerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "diner-service", url = "http://localhost:8081/api/v1/diners")
public interface DinerClient {

    @GetMapping("/{id}")
    DinerResponseDto findById(@PathVariable Long id);
}