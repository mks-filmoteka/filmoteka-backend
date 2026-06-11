package io.github.maxsouldrake.filmoteka.director.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request for updating a director")
public record DirectorRequest(
        @Schema(description = "Director name", example = "Lana Wachowski")
        @NotBlank
        @Size(max = 100)
        String name
) {
}
