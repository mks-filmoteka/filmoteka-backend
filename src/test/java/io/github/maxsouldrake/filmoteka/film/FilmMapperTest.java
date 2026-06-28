package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static io.github.maxsouldrake.filmoteka.film.FilmTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class FilmMapperTest {

    private final FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

    @Test
    void shouldMapFilmRequestToFilm() {
        Film film = filmMapper.filmRequestToFilm(filmRequest());

        assertThat(film.getTitle()).isEqualTo(FILM_TITLE);
        assertThat(film.getReleaseYear()).isEqualTo(RELEASE_YEAR);
        assertThat(film.getCountries()).containsExactlyInAnyOrder(Country.UNITED_STATES, Country.ITALY);
        assertThat(film.getDescription()).isEqualTo(FILM_DESCRIPTION);
        assertThat(film.getPosterUrl()).isEqualTo(FILM_POSTER_URL);
        assertThat(film.getGenres()).containsExactlyInAnyOrder(Genre.ADVENTURE, Genre.ACTION);
    }

    @Test
    void shouldMapFilmToDetailedFilmResponse() {
        DetailedFilmResponse response = filmMapper.filmToDetailedFilmResponse(loadedFilm());

        assertThat(response).isEqualTo(detailedFilmResponse());
    }

    @Test
    void shouldMapFilmToFilmResponse() {
        FilmResponse response = filmMapper.filmToFilmResponse(loadedFilm());

        assertThat(response).isEqualTo(filmResponse());
    }

    @Test
    void shouldMapUpdateFilmRequestToFilm() {
        Film film = loadedFilm();

        filmMapper.updateFilmRequestToFilm(updateFilmRequest(), film);

        assertThat(film.getTitle()).isEqualTo("updated title");
        assertThat(film.getReleaseYear()).isEqualTo(1999);
        assertThat(film.getCountries()).containsExactlyInAnyOrder(Country.CANADA);
        assertThat(film.getDescription()).isEqualTo(FILM_DESCRIPTION);
        assertThat(film.getGenres()).containsExactlyInAnyOrder(Genre.COMEDY);
    }
}