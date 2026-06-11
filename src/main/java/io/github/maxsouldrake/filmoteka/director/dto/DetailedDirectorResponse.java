package io.github.maxsouldrake.filmoteka.director.dto;

import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Director response with list of films")
public record DetailedDirectorResponse(
        @Schema(description = "Director id", example = "1")
        Long id,

        @Schema(description = "Director name", example = "Lana Wachowski")
        String name,

        @ArraySchema(schema = @Schema(implementation = FilmResponse.class))
        Set<FilmResponse> films
) {
}
