package com.example.aplicacion.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiDoc {
    @Bean
    public OpenAPI getOpenApi(){
        return new OpenAPI()
                .components(new Components())
                .info(
                        new Info()
                                .title("Aplicacion Antonio")
                                .description("aplicacion de peliculas y reseñas como FilmAffinity")
                );
    }
}
