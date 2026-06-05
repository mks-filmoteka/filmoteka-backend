package io.github.maxsouldrake.filmoteka.director.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DirectorRequest(
        @NotNull
        @Size(max = 100)
        String name
) {
}
