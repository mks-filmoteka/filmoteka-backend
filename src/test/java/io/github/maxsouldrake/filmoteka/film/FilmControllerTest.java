package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.common.PageResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static io.github.maxsouldrake.filmoteka.actor.ActorTestData.ACTOR_NAME;
import static io.github.maxsouldrake.filmoteka.director.DirectorTestData.DIRECTOR_NAME;
import static io.github.maxsouldrake.filmoteka.film.FilmTestData.*;
import static io.github.maxsouldrake.filmoteka.util.TestUtil.OBJECT_MAPPER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @MockitoBean
    private FilmService filmService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateFilm() throws Exception {
        when(filmService.createFilm(any(FilmRequest.class))).thenReturn(detailedFilmResponseFull());

        mockMvc.perform(
                post("/api/v1/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(filmRequestFull()))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(FILM_ID))
                .andExpect(jsonPath("$.title").value(FILM_TITLE))
                .andExpect(jsonPath("$.releaseYear").value(RELEASE_YEAR))
                .andExpect(jsonPath("$.country").value(FILM_COUNTRY))
                .andExpect(jsonPath("$.description").value(FILM_DESCRIPTION))
                .andExpect(jsonPath("$.posterUrl").value(FILM_POSTER_URL))
                .andExpect(jsonPath("$.actors[0].name").value(ACTOR_NAME))
                .andExpect(jsonPath("$.directors[0].name").value(DIRECTOR_NAME));

        verify(filmService).createFilm(any(FilmRequest.class));
    }

    @Test
    void shouldFindFilmById() throws Exception {
        when(filmService.findById(FILM_ID)).thenReturn(detailedFilmResponseFull());

        mockMvc.perform(get("/api/v1/films/{id}", FILM_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FILM_ID))
                .andExpect(jsonPath("$.title").value(FILM_TITLE))
                .andExpect(jsonPath("$.releaseYear").value(RELEASE_YEAR));

        verify(filmService).findById(FILM_ID);
    }

    @Test
    void shouldThrowIfFilmNotFound() throws Exception {
        when(filmService.findById(FILM_ID)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/films/{id}", FILM_ID)).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnPagedFilms() throws Exception {
        PageResponse<FilmResponse> response =
                new PageResponse<>(List.of(filmResponse()), 0, 100, 1, 1);
        when(filmService.getFilms(eq(null), any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(100))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.content[0].title").value(FILM_TITLE))
                .andExpect(jsonPath("$.content[0].id").value(FILM_ID));

        verify(filmService).getFilms(eq(null), any());
    }

    @Test
    void shouldReturnPagedEmptyList() throws Exception {
        PageResponse<FilmResponse> response =
                new PageResponse<>(List.of(), 0, 100, 0, 1);
        when(filmService.getFilms(eq(null), any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0));

        verify(filmService).getFilms(eq(null), any());
    }

    @Test
    void shouldReturnPagedFilmsByTitle() throws Exception {
        PageResponse<FilmResponse> response =
                new PageResponse<>(List.of(filmResponse()), 0, 100, 1, 1);
        when(filmService.getFilms(eq("film"), any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/films").param("title", "film"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value(FILM_TITLE));

        verify(filmService).getFilms(eq("film"), any());
    }

    @Test
    void shouldUpdateFilm() throws Exception {
        when(filmService.updateFilm(eq(FILM_ID), any(FilmRequest.class))).thenReturn(detailedFilmResponseFull());

        mockMvc.perform(
                        put("/api/v1/films/{id}", FILM_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(filmRequestFull()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(FILM_ID))
                .andExpect(jsonPath("$.title").value(FILM_TITLE));

        verify(filmService).updateFilm(eq(FILM_ID), any(FilmRequest.class));
    }

    @Test
    void shouldDeleteFilm() throws Exception {

        mockMvc.perform(delete("/api/v1/films/{id}", FILM_ID))
                .andExpect(status().isNoContent());

        verify(filmService).deleteFilm(FILM_ID);
    }
}