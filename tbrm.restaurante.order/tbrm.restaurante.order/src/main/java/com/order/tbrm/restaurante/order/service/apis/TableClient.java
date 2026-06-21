package com.order.tbrm.restaurante.order.service.apis;

import com.order.tbrm.restaurante.order.dto.TableResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "table-service", url = "http://localhost:8083/api/v1/tables")
public interface TableClient {

    @GetMapping("/{id}")
    TableResponseDto findById(@PathVariable Long id);
}