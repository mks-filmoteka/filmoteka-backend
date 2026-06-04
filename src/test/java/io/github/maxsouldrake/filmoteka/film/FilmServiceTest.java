package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.actor.Actor;
import io.github.maxsouldrake.filmoteka.actor.ActorService;
import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import io.github.maxsouldrake.filmoteka.actor.dto.ActorResponse;
import io.github.maxsouldrake.filmoteka.film.dto.CreateFilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

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

    @InjectMocks
    private FilmService filmService;

    @Test
    void shouldCreateFilm() {
        CreateFilmRequest request = new CreateFilmRequest(
                "test title",
                2000,
                "test country",
                "test description",
                "http://test",
                Set.of(Genre.ADVENTURE),
                Set.of(new ActorRequest("test name")), null);

        Film film = Film.builder()
                .title(request.title())
                .releaseYear(request.releaseYear())
                .country(request.country())
                .description(request.description())
                .posterUrl(request.posterUrl())
                .genres(request.genres())
                .build();

        Film savedFilm = Film.builder()
                .id(1L)
                .title(request.title())
                .releaseYear(request.releaseYear())
                .country(request.country())
                .description(request.description())
                .posterUrl(request.posterUrl())
                .genres(request.genres())
                .build();
        Actor actor = Actor.builder().id(1L).name("test name").build();
        savedFilm.addActor(actor);

        ActorResponse actorResponse = new ActorResponse(1L, "test name");

        DetailedFilmResponse filmResponse = new DetailedFilmResponse(
                1L,
                "test title",
                2000,
                "test country",
                "test description",
                "http://test",
                Set.of(Genre.ADVENTURE),
                Set.of(actorResponse),
                null
        );

        when(filmMapper.createFilmRequestToFilm(request)).thenReturn(film);
        when(actorService.findOrCreate(new ActorRequest("test name"))).thenReturn(actor);
        when(filmMapper.filmToDetailedFilmResponse(savedFilm)).thenReturn(filmResponse);
        when(filmRepository.save(any(Film.class))).thenReturn(savedFilm);

        DetailedFilmResponse response = filmService.create(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("test title");
        assertThat(response.releaseYear()).isEqualTo(2000);
        assertThat(response.country()).isEqualTo("test country");
        assertThat(response.genres()).containsExactlyInAnyOrder(Genre.ADVENTURE);
        assertThat(response.actors()).containsExactlyInAnyOrder(actorResponse);

        verify(filmMapper).createFilmRequestToFilm(request);
        verify(actorService).findOrCreate(new ActorRequest("test name"));
        verify(filmRepository).save(film);
        verify(filmMapper).filmToDetailedFilmResponse(savedFilm);
    }
}
