package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
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

        assertThat(response.id()).isEqualTo(FILM_ID);
        assertThat(response.title()).isEqualTo(FILM_TITLE);
        assertThat(response.releaseYear()).isEqualTo(RELEASE_YEAR);
        assertThat(response.country()).isEqualTo(FILM_COUNTRY);
        assertThat(response.description()).isEqualTo(FILM_DESCRIPTION);
        assertThat(response.posterUrl()).isEqualTo(FILM_POSTER_URL);
        assertThat(response.genres()).containsExactlyInAnyOrder(Genre.ADVENTURE, Genre.ACTION);
    }
}