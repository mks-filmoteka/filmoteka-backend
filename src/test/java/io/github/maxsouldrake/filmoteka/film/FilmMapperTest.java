package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static io.github.maxsouldrake.filmoteka.testdata.FilmTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class FilmMapperTest {

    private final FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

    @Test
    void shouldMapCreateFilmRequestToFilm() {
        Film film = filmMapper.createFilmRequestToFilm(createFilmRequest());

        assertThat(film.getTitle()).isEqualTo(FILM_TITLE);
        assertThat(film.getReleaseYear()).isEqualTo(RELEASE_YEAR);
        assertThat(film.getCountry()).isEqualTo(FILM_COUNTRY);
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
}