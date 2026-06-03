package io.github.maxsouldrake.filmoteka.film.dto;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import io.github.maxsouldrake.filmoteka.director.dto.DirectorRequest;
import io.github.maxsouldrake.filmoteka.film.Genre;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

public record CreateFilmRequest(
        @NotBlank
        @Size(max = 255)
        String title,

        @NotNull
        @Min(1888)
        @Max(2100)
        Integer releaseYear,

        @NotBlank
        @Size(max = 100)
        String country,

        @NotBlank
        @Size(max = 1000)
        String description,

        @NotBlank
        @URL
        String posterUrl,

        @NotEmpty
        @Size(max = 5)
        Set<@NotNull Genre> genres,

        Set<ActorRequest> actors,

        Set<DirectorRequest> directors
) {

}
