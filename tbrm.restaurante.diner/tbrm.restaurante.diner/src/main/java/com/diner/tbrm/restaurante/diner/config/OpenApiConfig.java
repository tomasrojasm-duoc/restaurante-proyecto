package com.diner.tbrm.restaurante.diner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI dinerServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Diner API")
                        .description("Documentación de endpoints para la gestión de comensales del restaurante.")
                        .version("1.0.0"));
    }
}