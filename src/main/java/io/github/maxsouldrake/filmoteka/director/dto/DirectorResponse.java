package io.github.maxsouldrake.filmoteka.director.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Director response")
public record DirectorResponse(
        @Schema(description = "Director id", example = "1")
        Long id,

        @Schema(description = "Director name", example = "Lana Wachowski")
        String name
) {
}
