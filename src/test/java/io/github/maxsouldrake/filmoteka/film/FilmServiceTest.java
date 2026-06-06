package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.actor.ActorService;
import io.github.maxsouldrake.filmoteka.director.DirectorService;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.github.maxsouldrake.filmoteka.testdata.ActorTestData.*;
import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.*;
import static io.github.maxsouldrake.filmoteka.testdata.FilmTestData.*;
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
        when(filmMapper.filmToDetailedFilmResponse(loadedFilm)).thenReturn(detailedFilmResponse());
        when(filmRepository.save(any(Film.class))).thenReturn(loadedFilm);

        DetailedFilmResponse response = filmService.create(createFilmRequestFull());

        assertThat(response).isEqualTo(detailedFilmResponse());

        verify(filmMapper).createFilmRequestToFilm(createFilmRequestFull());
        verify(actorService).findOrCreate(actorRequest());
        verify(directorService).findOrCreate(directorRequest());
        verify(filmRepository).save(film);
        verify(filmMapper).filmToDetailedFilmResponse(loadedFilm);
    }
}
