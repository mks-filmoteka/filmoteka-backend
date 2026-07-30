package io.github.mksfilmoteka.catalog.director;

import io.github.mksfilmoteka.catalog.common.exception.ConflictException;
import io.github.mksfilmoteka.catalog.common.exception.ErrorCode;
import io.github.mksfilmoteka.catalog.common.exception.ResourceNotFoundException;
import io.github.mksfilmoteka.catalog.director.dto.DirectorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static io.github.mksfilmoteka.catalog.director.DirectorTestData.*;
import static io.github.mksfilmoteka.catalog.util.TestUtil.OBJECT_MAPPER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
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
        String message = "Director with id " + DIRECTOR_ID + " not found";
        when(directorService.findById(DIRECTOR_ID)).thenThrow(new ResourceNotFoundException(message));

        mockMvc.perform(get("/api/v1/directors/{id}", DIRECTOR_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.name()));
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
    void shouldThrowOnUpdateIfConflict() throws Exception {
        String message = "Director with name " + DIRECTOR_NAME + " already exists";
        when(directorService.updateDirector(eq(DIRECTOR_ID), any(DirectorRequest.class)))
                .thenThrow(new ConflictException(message));

        mockMvc.perform(
                        put("/api/v1/directors/{id}", DIRECTOR_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(directorRequest()))
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.code").value(ErrorCode.CONFLICT.name()));

        verify(directorService).updateDirector(eq(DIRECTOR_ID), any(DirectorRequest.class));
    }

    @Test
    void shouldThrowOnUpdateIfInvalidRequest() throws Exception {
        mockMvc.perform(
                        put("/api/v1/directors/{id}", DIRECTOR_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(new DirectorRequest("")))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.VALIDATION_FAILED.name()))
                .andExpect(jsonPath("$.errorDetails[0].field").value("name"));

        verify(directorService, never()).updateDirector(eq(DIRECTOR_ID), any(DirectorRequest.class));
    }

    @Test
    void shouldDeleteDirector() throws Exception {

        mockMvc.perform(delete("/api/v1/directors/{id}", DIRECTOR_ID))
                .andExpect(status().isNoContent());

        verify(directorService).deleteDirector(DIRECTOR_ID);
    }

    @Test
    void shouldThrowOnDeleteIfDirectorNotFound() throws Exception {
        String message = "Director with id " + DIRECTOR_ID + " not found";
        doThrow(new ResourceNotFoundException(message)).when(directorService).deleteDirector(DIRECTOR_ID);

        mockMvc.perform(delete("/api/v1/directors/{id}", DIRECTOR_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.name()));

        verify(directorService).deleteDirector(DIRECTOR_ID);
    }
}
