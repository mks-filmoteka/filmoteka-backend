package io.github.mksfilmoteka.backend.film.dto;

import io.github.mksfilmoteka.backend.actor.dto.ActorResponse;
import io.github.mksfilmoteka.backend.director.dto.DirectorResponse;
import io.github.mksfilmoteka.backend.film.Country;
import io.github.mksfilmoteka.backend.film.Genre;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Detailed film response with actors and directors")
public record DetailedFilmResponse(
        @Schema(description = "Film id", example = "1")
        Long id,

        @Schema(description = "Film title", example = "The Matrix")
        String title,

        @Schema(description = "Film release year", example = "1999")
        Integer releaseYear,

        @Schema(description = "Film countries of production", example = "[\"United States\", \"Italy\"]")
        List<Country> countries,

        @Schema(description = "Film description", example = "film description")
        String description,

        @Schema(description = "Film poster name", example = "00000000-0000-0000-0000-000000000000.jpg")
        String posterName,

        @Schema(description = "Film genres", example = "[\"Action\", \"Adventure\"]")
        List<Genre> genres,

        @ArraySchema(schema = @Schema(implementation = ActorResponse.class))
        List<ActorResponse> actors,

        @ArraySchema(schema = @Schema(implementation = DirectorResponse.class))
        List<DirectorResponse> directors
) {
}
