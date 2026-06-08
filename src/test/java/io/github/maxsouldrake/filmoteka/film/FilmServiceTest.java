package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.actor.ActorService;
import io.github.maxsouldrake.filmoteka.common.PageResponse;
import io.github.maxsouldrake.filmoteka.director.DirectorService;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static io.github.maxsouldrake.filmoteka.actor.ActorTestData.actorRequest;
import static io.github.maxsouldrake.filmoteka.actor.ActorTestData.loadedActor;
import static io.github.maxsouldrake.filmoteka.director.DirectorTestData.directorRequest;
import static io.github.maxsouldrake.filmoteka.director.DirectorTestData.loadedDirector;
import static io.github.maxsouldrake.filmoteka.film.FilmTestData.*;
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

        when(filmMapper.filmRequestToFilm(filmRequestFull())).thenReturn(film);
        when(actorService.findOrCreate(actorRequest())).thenReturn(loadedActor());
        when(directorService.findOrCreate(directorRequest())).thenReturn(loadedDirector());
        when(filmMapper.filmToDetailedFilmResponse(loadedFilm)).thenReturn(detailedFilmResponseFull());
        when(filmRepository.save(any(Film.class))).thenReturn(loadedFilm);

        DetailedFilmResponse response = filmService.createFilm(filmRequestFull());

        assertThat(response).isEqualTo(detailedFilmResponseFull());

        verify(filmMapper).filmRequestToFilm(filmRequestFull());
        verify(actorService).findOrCreate(actorRequest());
        verify(directorService).findOrCreate(directorRequest());
        verify(filmRepository).save(film);
        verify(filmMapper).filmToDetailedFilmResponse(loadedFilm);
    }

    @Test
    void shouldFindFilmByIdIfExists() {
        Film loadedFilm = loadedFilm();

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
    void shouldReturnPagedFilmsWhenTitleNull() {
        List<Film> films = List.of(loadedFilm());
        Pageable pageable = PageRequest.of(0, 100);
        Page<Film> page = new PageImpl<>(films, pageable, films.size());
        when(filmRepository.findAll(pageable)).thenReturn(page);
        when(filmMapper.filmsToFilmResponses(page.getContent())).thenReturn(List.of(filmResponse()));

        PageResponse<FilmResponse> response = filmService.getFilms(null, pageable);

        assertThat(response.content()).containsExactly(filmResponse());
        assertThat(response.size()).isEqualTo(100);
        assertThat(response.totalElements()).isEqualTo(1);

        verify(filmRepository).findAll(pageable);
        verify(filmRepository, never()).findByTitleContainingIgnoreCase(anyString(), any(Pageable.class));
        verify(filmMapper).filmsToFilmResponses(films);
    }

    @Test
    void shouldReturnPagedFilmsWhenTitleBlank() {
        List<Film> films = List.of(loadedFilm());
        Pageable pageable = PageRequest.of(0, 100);
        Page<Film> page = new PageImpl<>(films, pageable, films.size());
        when(filmRepository.findAll(pageable)).thenReturn(page);
        when(filmMapper.filmsToFilmResponses(page.getContent())).thenReturn(List.of(filmResponse()));

        PageResponse<FilmResponse> response = filmService.getFilms(" ", pageable);

        assertThat(response.content()).containsExactly(filmResponse());
        assertThat(response.size()).isEqualTo(100);
        assertThat(response.totalElements()).isEqualTo(1);

        verify(filmRepository).findAll(pageable);
        verify(filmRepository, never()).findByTitleContainingIgnoreCase(anyString(), any(Pageable.class));
        verify(filmMapper).filmsToFilmResponses(films);
    }

    @Test
    void shouldReturnFilmsByTitle() {
        List<Film> films = List.of(loadedFilm());
        Page<Film> page = new PageImpl<>(films);
        Pageable pageable = PageRequest.of(0, 100);
        when(filmRepository.findByTitleContainingIgnoreCase("film", pageable)).thenReturn(page);
        when(filmMapper.filmsToFilmResponses(page.getContent())).thenReturn(List.of(filmResponse()));

        PageResponse<FilmResponse> response = filmService.getFilms("film", pageable);

        assertThat(response.content()).containsExactly(filmResponse());
        assertThat(response.totalElements()).isEqualTo(1);

        verify(filmRepository).findByTitleContainingIgnoreCase("film", pageable);
        verify(filmRepository, never()).findAll();
        verify(filmMapper).filmsToFilmResponses(films);
    }

    @Test
    void shouldReturnEmptyListIfNotExist() {
        List<Film> films = List.of();
        Page<Film> page = new PageImpl<>(films);
        Pageable pageable = PageRequest.of(0, 100);
        when(filmRepository.findAll(pageable)).thenReturn(page);
        when(filmMapper.filmsToFilmResponses(List.of())).thenReturn(List.of());

        PageResponse<FilmResponse> response = filmService.getFilms(null, pageable);

        assertThat(response.content()).isEmpty();

        verify(filmRepository).findAll(pageable);
        verify(filmMapper).filmsToFilmResponses(List.of());
    }

    @Test
    void shouldUpdateFilmIfExists() {
        Film loadedFilm = loadedFilm();
        loadedFilm.setTitle("old title");

        when(filmRepository.findById(FILM_ID)).thenReturn(Optional.of(loadedFilm));
        when(actorService.findOrCreate(any())).thenReturn(loadedActor());
        when(directorService.findOrCreate(any())).thenReturn(loadedDirector());

        doAnswer(updateTitleOnly()).when(filmMapper).updateFilmRequestToFilm(any(), any());

        when(filmRepository.save(any(Film.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(filmMapper.filmToDetailedFilmResponse(any(Film.class))).thenReturn(detailedFilmResponseFull());

        DetailedFilmResponse response = filmService.updateFilm(FILM_ID, filmRequestFull());

        assertThat(response).isEqualTo(detailedFilmResponseFull());
        ArgumentCaptor<Film> captor = ArgumentCaptor.forClass(Film.class);

        verify(filmRepository).save(captor.capture());
        assertThat(captor.getValue().getTitle()).isEqualTo(FILM_TITLE);
        verify(filmMapper).updateFilmRequestToFilm(filmRequestFull(), loadedFilm);
        verify(actorService).findOrCreate(actorRequest());
        verify(directorService).findOrCreate(directorRequest());
        verify(filmMapper).filmToDetailedFilmResponse(any(Film.class));
    }

    @Test
    void shouldDeleteFilmIfExists() {
        filmService.deleteFilm(FILM_ID);

        verify(filmRepository).deleteById(FILM_ID);
    }

    private static Answer<Void> updateTitleOnly() {
        return invocation -> {
            FilmRequest request = invocation.getArgument(0);
            Film film = invocation.getArgument(1);
            film.setTitle(request.title());
            return null;
        };
    }
}
