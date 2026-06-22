package com.notification.tbrm.restaurante.notification.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI notificationServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Notification API")
                        .description("Documentación de endpoints para la gestión de notificaciones, plantillas y canales.")
                        .version("1.0.0"));
    }
}