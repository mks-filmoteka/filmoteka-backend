package io.github.maxsouldrake.filmoteka.director.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DirectorRequest(
        @NotBlank
        @Size(max = 100)
        String name
) {
}
