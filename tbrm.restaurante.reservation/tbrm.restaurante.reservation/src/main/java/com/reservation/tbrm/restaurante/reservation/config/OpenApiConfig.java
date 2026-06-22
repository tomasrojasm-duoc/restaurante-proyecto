package com.reservation.tbrm.restaurante.reservation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI reservationServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Reservation API")
                        .description("Documentación de endpoints para la gestión de reservas del restaurante.")
                        .version("1.0.0"));
    }
}