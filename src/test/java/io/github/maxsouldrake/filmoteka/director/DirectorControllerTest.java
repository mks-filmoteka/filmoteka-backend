package io.github.maxsouldrake.filmoteka.director;

import io.github.maxsouldrake.filmoteka.director.dto.DirectorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static io.github.maxsouldrake.filmoteka.director.DirectorTestData.*;
import static io.github.maxsouldrake.filmoteka.util.TestUtil.OBJECT_MAPPER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DirectorController.class)
class DirectorControllerTest {

    @MockitoBean
    private DirectorService directorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFindDirectorById() throws Exception {
        when(directorService.findById(DIRECTOR_ID)).thenReturn(detailedDirectorResponse());

        mockMvc.perform(get("/api/v1/directors/{id}", DIRECTOR_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(DIRECTOR_ID))
                .andExpect(jsonPath("$.name").value(DIRECTOR_NAME));

        verify(directorService).findById(DIRECTOR_ID);
    }

    @Test
    void shouldThrowIfDirectorNotFound() throws Exception {
        when(directorService.findById(DIRECTOR_ID)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/directors/{id}", DIRECTOR_ID)).andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateDirector() throws Exception {
        when(directorService.updateDirector(eq(DIRECTOR_ID), any(DirectorRequest.class))).thenReturn(detailedDirectorResponse());

        mockMvc.perform(
                        put("/api/v1/directors/{id}", DIRECTOR_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(directorRequest()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(DIRECTOR_ID))
                .andExpect(jsonPath("$.name").value(DIRECTOR_NAME));

        verify(directorService).updateDirector(eq(DIRECTOR_ID), any(DirectorRequest.class));
    }

    @Test
    void shouldDeleteDirector() throws Exception {

        mockMvc.perform(delete("/api/v1/directors/{id}", DIRECTOR_ID))
                .andExpect(status().isNoContent());

        verify(directorService).deleteDirector(DIRECTOR_ID);
    }
}