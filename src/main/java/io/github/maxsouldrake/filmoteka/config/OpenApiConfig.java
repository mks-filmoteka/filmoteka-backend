package io.github.maxsouldrake.filmoteka.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI filmotekaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Filmoteka API")
                        .version("v1")
                        .description("Movie management system API"));
    }

    @Bean
    public GroupedOpenApi filmApi() {
        return GroupedOpenApi.builder().group("films").pathsToMatch("/api/v1/films/**").build();
    }

    @Bean
    public GroupedOpenApi actorApi() {
        return GroupedOpenApi.builder().group("actors").pathsToMatch("/api/v1/actors/**").build();
    }

    @Bean
    public GroupedOpenApi directorApi() {
        return GroupedOpenApi.builder().group("directors").pathsToMatch("/api/v1/directors/**").build();
    }
}
