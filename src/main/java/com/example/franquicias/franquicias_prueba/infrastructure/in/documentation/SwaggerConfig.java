package com.example.franquicias.franquicias_prueba.infrastructure.in.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Api franquicias")
                        .version("1.0")
                        .description("Documentación de los endpoints de la api de franquicias")
                        .contact(new Contact()
                                .name("Laur Daniela Jaimes")
                                .email("laura.jaimes@pragma.com.co")
                        )
                );
    }
}
