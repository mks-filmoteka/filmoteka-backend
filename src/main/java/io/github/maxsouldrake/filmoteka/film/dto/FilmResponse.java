package io.github.maxsouldrake.filmoteka.film.dto;

import io.github.maxsouldrake.filmoteka.film.Country;
import io.github.maxsouldrake.filmoteka.film.Genre;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Simple film response")
public record FilmResponse(
        @Schema(description = "Film id", example = "1")
        Long id,

        @Schema(description = "Film title", example = "The Matrix")
        String title,

        @Schema(description = "Film countries of production", example = "[\"United States\", \"Italy\"]")
        List<Country> countries,

        @Schema(description = "Film release year", example = "1999")
        Integer releaseYear,

        @Schema(description = "Film poster name", example = "00000000-0000-0000-0000-000000000000.jpg")
        String posterName,

        @Schema(description = "Film genres", example = "[\"Action\", \"Adventure\"]")
        List<Genre> genres
) {
}
