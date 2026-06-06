package io.github.maxsouldrake.filmoteka.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxsouldrake.filmoteka.film.dto.CreateFilmRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static io.github.maxsouldrake.filmoteka.testdata.ActorTestData.ACTOR_NAME;
import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.DIRECTOR_NAME;
import static io.github.maxsouldrake.filmoteka.testdata.FilmTestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        when(filmService.create(any(CreateFilmRequest.class))).thenReturn(detailedFilmResponse());

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
}