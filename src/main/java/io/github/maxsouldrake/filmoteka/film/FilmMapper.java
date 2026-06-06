package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.CreateFilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.UpdateFilmRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "directors", ignore = true)
    Film createFilmRequestToFilm(CreateFilmRequest request);
    Film updateFilmRequestToFilm(UpdateFilmRequest request);
    FilmResponse filmToFilmResponse(Film film);
    DetailedFilmResponse filmToDetailedFilmResponse(Film film);
    List<FilmResponse> filmsToFilmResponses(List<Film> films);
}
