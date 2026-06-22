package com.waiter.tbrm.restaurante.waiter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI waiterServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Waiter Service API")
                        .description("Documentación de endpoints para la gestión de garzones del restaurante.")
                        .version("1.0.0"));
    }
}