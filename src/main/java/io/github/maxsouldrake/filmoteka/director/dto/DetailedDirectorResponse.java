package io.github.maxsouldrake.filmoteka.director.dto;

import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;

import java.util.Set;

public record DetailedDirectorResponse(
        Long id,
        String name,
        Set<FilmResponse> films
) {
}
