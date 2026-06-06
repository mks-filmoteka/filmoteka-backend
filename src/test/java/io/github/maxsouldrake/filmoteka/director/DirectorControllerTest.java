package io.github.maxsouldrake.filmoteka.director;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}