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

    @Mock
    private FilmMapper filmMapper;

    @InjectMocks
    private FilmService filmService;

    @Test
    void shouldCreateFilm() {
        CreateFilmRequest request = new CreateFilmRequest(
                "test title",
                2000,
                "test country",
                null, null, null, null, null);

        Film film = Film.builder()
                .title(request.title())
                .releaseYear(request.releaseYear())
                .country(request.country())
                .build();

        Film savedFilm = Film.builder()
                .id(1L)
                .title(request.title())
                .releaseYear(request.releaseYear())
                .country(request.country())
                .build();

        DetailedFilmResponse filmResponse = new DetailedFilmResponse(
                1L,
                "test title",
                2000,
                "test country",
                null, null, null, null, null
        );

        when(filmMapper.createFilmRequestToFilm(request)).thenReturn(film);
        when(filmMapper.filmToDetailedFilmResponse(savedFilm)).thenReturn(filmResponse);
        when(filmRepository.save(any(Film.class))).thenReturn(savedFilm);

        DetailedFilmResponse response = filmService.create(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("test title");
        assertThat(response.releaseYear()).isEqualTo(2000);
        assertThat(response.country()).isEqualTo("test country");

        verify(filmMapper).createFilmRequestToFilm(request);
        verify(filmRepository).save(film);
        verify(filmMapper).filmToDetailedFilmResponse(savedFilm);
    }
}