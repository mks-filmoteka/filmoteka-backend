package io.github.maxsouldrake.filmoteka.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxsouldrake.filmoteka.film.dto.CreateFilmRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static io.github.maxsouldrake.filmoteka.testdata.ActorTestData.ACTOR_NAME;
import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.DIRECTOR_NAME;
import static io.github.maxsouldrake.filmoteka.testdata.FilmTestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
class FilmControllerTest {

    @MockitoBean
    private FilmService filmService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void shouldCreateFilm() throws Exception {
        when(filmService.create(any(CreateFilmRequest.class))).thenReturn(detailedFilmResponseFull());

        mockMvc.perform(
                post("/api/v1/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createFilmRequestFull()))
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

        verify(filmService).create(any(CreateFilmRequest.class));
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
    void shouldReturnFilms() throws Exception {
        when(filmService.findAll()).thenReturn(List.of(filmResponse()));

        mockMvc.perform(get("/api/v1/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("film title"));

        verify(filmService).findAll();
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        when(filmService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(filmService).findAll();
    }
}