package io.github.maxsouldrake.filmoteka.film.dto;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorResponse;
import io.github.maxsouldrake.filmoteka.director.dto.DirectorResponse;
import io.github.maxsouldrake.filmoteka.film.Genre;
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

        @Schema(description = "Film country of production", example = "USA")
        String country,

        @Schema(description = "Film description", example = "film description")
        String description,

        @Schema(description = "Url of film poster", example = "https://film_poster")
        String posterUrl,

        @Schema(description = "Film genres", example = "[\"ACTION\", \"ADVENTURE\"]")
        List<Genre> genres,

        @ArraySchema(schema = @Schema(implementation = ActorResponse.class))
        List<ActorResponse> actors,

        @ArraySchema(schema = @Schema(implementation = DirectorResponse.class))
        List<DirectorResponse> directors
) {
}
