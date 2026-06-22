package com.table.tbrm.restaurante.table.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI tableServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Table Service API")
                        .description("Documentación de endpoints para la gestión de mesas del restaurante.")
                        .version("1.0.0"));
    }
}