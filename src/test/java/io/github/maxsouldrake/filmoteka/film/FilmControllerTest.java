package io.github.maxsouldrake.filmoteka.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import io.github.maxsouldrake.filmoteka.actor.dto.ActorResponse;
import io.github.maxsouldrake.filmoteka.film.dto.CreateFilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

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
        CreateFilmRequest request = new CreateFilmRequest(
                "test title",
                2000,
                "test country",
                "test description",
                "http://test",
                Set.of(Genre.ADVENTURE),
                Set.of(new ActorRequest("test name")),
                null
        );

        DetailedFilmResponse response = new DetailedFilmResponse(
                1L,
                "test title",
                2000,
                "test country",
                "test description",
                "http://test",
                Set.of(Genre.ADVENTURE),
                Set.of(new ActorResponse(1L, "test name")),
                null
        );

        when(filmService.create(any(CreateFilmRequest.class))).thenReturn(response);

        mockMvc.perform(
                post("/api/v1/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("test title"))
                .andExpect(jsonPath("$.releaseYear").value(2000))
                .andExpect(jsonPath("$.country").value("test country"))
                .andExpect(jsonPath("$.description").value("test description"))
                .andExpect(jsonPath("$.posterUrl").value("http://test"))
                .andExpect(jsonPath("$.actors[0].name").value("test name"));

        verify(filmService).create(any(CreateFilmRequest.class));


    }
}