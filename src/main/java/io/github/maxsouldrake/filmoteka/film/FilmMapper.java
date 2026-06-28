package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "directors", ignore = true)
    Film filmRequestToFilm(FilmRequest request);

    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "directors", ignore = true)
    void updateFilmRequestToFilm(FilmRequest request, @MappingTarget Film film);

    FilmResponse filmToFilmResponse(Film film);
    DetailedFilmResponse filmToDetailedFilmResponse(Film film);
    List<FilmResponse> filmsToFilmResponses(List<Film> films);

    @AfterMapping
    default void normalizeEnums(FilmRequest request, @MappingTarget Film film) {
        if (request.genres() != null) {
            film.setGenres(request.genres().stream().distinct().toList());
        }
        if (request.countries() != null) {
            film.setCountries(request.countries().stream().distinct().toList());
        }
    }
}
