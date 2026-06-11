package io.github.maxsouldrake.filmoteka.actor.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Actor response")
public record ActorResponse(
        @Schema(description = "Actor id", example = "1")
        Long id,

        @Schema(description = "Actor name", example = "Keanu Reeves")
        String name
) {
}
