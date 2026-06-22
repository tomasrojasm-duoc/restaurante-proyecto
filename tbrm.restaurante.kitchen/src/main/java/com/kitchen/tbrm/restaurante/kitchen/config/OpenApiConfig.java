package com.kitchen.tbrm.restaurante.kitchen.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI kitchenServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kitchen API")
                        .description("Documentación de endpoints para la gestión de tickets de cocina.")
                        .version("1.0.0"));
    }
}