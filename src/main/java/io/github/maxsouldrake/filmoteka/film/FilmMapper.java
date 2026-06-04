package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    @Mapping(target = "actors", ignore = true)
    Film createFilmRequestToFilm(CreateFilmRequest request);
    Film updateFilmRequestToFilm(UpdateFilmRequest request);
    BasicFilmResponse filmToBasicFilmResponse(Film film);
    FilmResponse filmToFilmResponse(Film film);
    DetailedFilmResponse filmToDetailedFilmResponse(Film film);
}
