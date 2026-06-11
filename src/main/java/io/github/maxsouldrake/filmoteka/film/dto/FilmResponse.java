package io.github.maxsouldrake.filmoteka.film.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Simple film response")
public record FilmResponse(
        @Schema(description = "Film id", example = "1")
        Long id,

        @Schema(description = "Film title", example = "The Matrix")
        String title,

        @Schema(description = "Film release year", example = "1999")
        Integer releaseYear,

        @Schema(description = "Url of film poster", example = "https://film_poster")
        String posterUrl
) {
}
