package com.example.employeeservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Esta clase configura Swagger / OpenAPI.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Este bean define la información general de la API.
     * @return configuración básica del documento OpenAPI
     */
    @Bean
    public OpenAPI employeeServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de servicio para empleados")
                        .description("API de evaluación técnica")
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación del proyecto"));
    }
}