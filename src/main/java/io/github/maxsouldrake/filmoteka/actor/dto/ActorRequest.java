package io.github.maxsouldrake.filmoteka.actor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActorRequest(
        @NotBlank
        @Size(max = 100)
        String name
) {
}
