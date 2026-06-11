package io.github.maxsouldrake.filmoteka.actor.dto;

import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Actor response with list of films")
public record DetailedActorResponse(
        @Schema(description = "Actor id", example = "1")
        Long id,

        @Schema(description = "Actor name", example = "Keanu Reeves")
        String name,

        @ArraySchema(schema = @Schema(implementation = FilmResponse.class))
        Set<FilmResponse> films
) {
}
