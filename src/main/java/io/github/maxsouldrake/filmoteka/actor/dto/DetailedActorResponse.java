package io.github.maxsouldrake.filmoteka.actor.dto;

import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;

import java.util.Set;

public record DetailedActorResponse(
        Long id,
        String name,
        Set<FilmResponse> films
) {
}
