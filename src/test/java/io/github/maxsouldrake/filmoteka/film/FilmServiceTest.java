package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.CreateFilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    @Test
    void shouldCreateFilm() {
        CreateFilmRequest request = new CreateFilmRequest(
                "test title",
                2000,
                "test country",
                null, null, null, null, null);
        Film savedFilm = Film.builder()
                .id(1L)
                .title(request.title())
                .releaseYear(request.releaseYear())
                .country(request.country())
                .description(request.description())
                .genres(request.genres())
                .build();

        when(filmRepository.save(any(Film.class))).thenReturn(savedFilm);

        DetailedFilmResponse response = filmService.create(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("test title");
        assertThat(response.releaseYear()).isEqualTo(2000);
        assertThat(response.country()).isEqualTo("test country");

        verify(filmRepository).save(any(Film.class));
    }
}