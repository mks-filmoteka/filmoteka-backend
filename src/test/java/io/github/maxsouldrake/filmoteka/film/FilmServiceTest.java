package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.actor.ActorService;
import io.github.maxsouldrake.filmoteka.director.DirectorService;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import io.github.maxsouldrake.filmoteka.testdata.FilmTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static io.github.maxsouldrake.filmoteka.testdata.ActorTestData.actorRequest;
import static io.github.maxsouldrake.filmoteka.testdata.ActorTestData.loadedActor;
import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.directorRequest;
import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.loadedDirector;
import static io.github.maxsouldrake.filmoteka.testdata.FilmTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private FilmMapper filmMapper;

    @Mock
    private ActorService actorService;

    @Mock
    private DirectorService directorService;

    @InjectMocks
    private FilmService filmService;

    @Test
    void shouldCreateFilm() {
        Film film = film();
        Film loadedFilm = loadedFilm();
        loadedFilm.addActor(loadedActor());
        loadedFilm.addDirector(loadedDirector());

        when(filmMapper.createFilmRequestToFilm(createFilmRequestFull())).thenReturn(film);
        when(actorService.findOrCreate(actorRequest())).thenReturn(loadedActor());
        when(directorService.findOrCreate(directorRequest())).thenReturn(loadedDirector());
        when(filmMapper.filmToDetailedFilmResponse(loadedFilm)).thenReturn(detailedFilmResponseFull());
        when(filmRepository.save(any(Film.class))).thenReturn(loadedFilm);

        DetailedFilmResponse response = filmService.create(createFilmRequestFull());

        assertThat(response).isEqualTo(detailedFilmResponseFull());

        verify(filmMapper).createFilmRequestToFilm(createFilmRequestFull());
        verify(actorService).findOrCreate(actorRequest());
        verify(directorService).findOrCreate(directorRequest());
        verify(filmRepository).save(film);
        verify(filmMapper).filmToDetailedFilmResponse(loadedFilm);
    }

    @Test
    void shouldFindFilmByIdIfExists() {
        Film loadedFilm = FilmTestData.loadedFilm();

        when(filmRepository.findById(FILM_ID)).thenReturn(Optional.of(loadedFilm));
        when(filmMapper.filmToDetailedFilmResponse(loadedFilm)).thenReturn(detailedFilmResponseFull());

        DetailedFilmResponse response = filmService.findById(FILM_ID);

        assertThat(response).isEqualTo(detailedFilmResponseFull());
        verify(filmRepository).findById(FILM_ID);
        verify(filmMapper).filmToDetailedFilmResponse(loadedFilm);
    }

    @Test
    void shouldThrowIfDoesNotExist() {
        when(filmRepository.findById(FILM_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> filmService.findById(FILM_ID));

        verify(filmRepository).findById(FILM_ID);
        verifyNoInteractions(filmMapper);
    }

    @Test
    void shouldFindFilmsIfExist() {
        List<Film> films = List.of(loadedFilm());
        when(filmRepository.findAll()).thenReturn(films);
        when(filmMapper.filmsToFilmResponses(films)).thenReturn(List.of(filmResponse()));

        List<FilmResponse> response = filmService.findAll();

        assertThat(response).hasSize(1);
        assertThat(response).containsExactly(filmResponse());

        verify(filmRepository).findAll();
        verify(filmMapper).filmsToFilmResponses(films);
    }

    @Test
    void shouldReturnEmptyListIfNotExist() {
        when(filmRepository.findAll()).thenReturn(List.of());
        when(filmMapper.filmsToFilmResponses(List.of())).thenReturn(List.of());

        List<FilmResponse> response = filmService.findAll();

        assertThat(response).isEmpty();

        verify(filmRepository).findAll();
        verify(filmMapper).filmsToFilmResponses(List.of());
    }
}
