package io.github.maxsouldrake.filmoteka.actor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request for updating an actor")
public record ActorRequest(
        @Schema(description = "Actor name", example = "Keanu Reeves")
        @NotBlank
        @Size(max = 100)
        String name
) {
}
