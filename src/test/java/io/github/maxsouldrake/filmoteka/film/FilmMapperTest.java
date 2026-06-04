package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.CreateFilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FilmMapperTest {

    private final FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

    @Test
    void shouldMapCreateFilmRequestToFilm() {
        CreateFilmRequest request = new CreateFilmRequest(
                "test title",
                2000,
                "test country",
                "test description",
                "http://test",
                Set.of(Genre.ADVENTURE),
                null,
                null
        );

        Film film = filmMapper.createFilmRequestToFilm(request);

        assertThat(film.getTitle()).isEqualTo("test title");
        assertThat(film.getReleaseYear()).isEqualTo(2000);
        assertThat(film.getCountry()).isEqualTo("test country");
        assertThat(film.getDescription()).isEqualTo("test description");
        assertThat(film.getPosterUrl()).isEqualTo("http://test");
        assertThat(film.getGenres()).contains(Genre.ADVENTURE);
    }

    @Test
    void shouldMapFilmToDetailedFilmResponse() {
        Film film = Film.builder()
                .id(1L)
                .title("test title")
                .releaseYear(2000)
                .country("test country")
                .description("test description")
                .posterUrl("http://test")
                .genres(Set.of(Genre.ADVENTURE))
                .build();

        DetailedFilmResponse response = filmMapper.filmToDetailedFilmResponse(film);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("test title");
        assertThat(response.releaseYear()).isEqualTo(2000);
        assertThat(response.country()).isEqualTo("test country");
        assertThat(response.description()).isEqualTo("test description");
        assertThat(response.posterUrl()).isEqualTo("http://test");
        assertThat(response.genres()).contains(Genre.ADVENTURE);
    }
}