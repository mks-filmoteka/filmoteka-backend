package io.github.mksfilmoteka.catalog.actor;

import io.github.mksfilmoteka.catalog.actor.dto.ActorRequest;
import io.github.mksfilmoteka.catalog.common.exception.ConflictException;
import io.github.mksfilmoteka.catalog.common.exception.ErrorCode;
import io.github.mksfilmoteka.catalog.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static io.github.mksfilmoteka.catalog.actor.ActorTestData.*;
import static io.github.mksfilmoteka.catalog.util.TestUtil.OBJECT_MAPPER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActorController.class)
class ActorControllerTest {

    @MockitoBean
    private ActorService actorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFindActorById() throws Exception {
        when(actorService.findById(ACTOR_ID)).thenReturn(detailedActorResponse());

        mockMvc.perform(get("/api/v1/actors/{id}", ACTOR_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ACTOR_ID))
                .andExpect(jsonPath("$.name").value(ACTOR_NAME));

        verify(actorService).findById(ACTOR_ID);
    }

    @Test
    void shouldThrowIfActorNotFound() throws Exception {
        String message = "Actor with id " + ACTOR_ID + " not found";
        when(actorService.findById(ACTOR_ID)).thenThrow(new ResourceNotFoundException(message));

        mockMvc.perform(get("/api/v1/actors/{id}", ACTOR_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.name()));
    }

    @Test
    void shouldUpdateActor() throws Exception {
        when(actorService.updateActor(eq(ACTOR_ID), any(ActorRequest.class))).thenReturn(detailedActorResponse());

        mockMvc.perform(
                        put("/api/v1/actors/{id}", ACTOR_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(actorRequest()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ACTOR_ID))
                .andExpect(jsonPath("$.name").value(ACTOR_NAME));

        verify(actorService).updateActor(eq(ACTOR_ID), any(ActorRequest.class));
    }

    @Test
    void shouldThrowOnUpdateIfConflict() throws Exception {
        String message = "Actor with name " + ACTOR_NAME + " already exists";
        when(actorService.updateActor(eq(ACTOR_ID), any(ActorRequest.class)))
                .thenThrow(new ConflictException(message));

        mockMvc.perform(
                        put("/api/v1/actors/{id}", ACTOR_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(actorRequest()))
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.code").value(ErrorCode.CONFLICT.name()));

        verify(actorService).updateActor(eq(ACTOR_ID), any(ActorRequest.class));
    }

    @Test
    void shouldThrowOnUpdateIfInvalidRequest() throws Exception {
        mockMvc.perform(
                        put("/api/v1/actors/{id}", ACTOR_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsString(new ActorRequest("")))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.VALIDATION_FAILED.name()))
                .andExpect(jsonPath("$.errorDetails[0].field").value("name"));

        verify(actorService, never()).updateActor(eq(ACTOR_ID), any(ActorRequest.class));
    }

    @Test
    void shouldDeleteActor() throws Exception {

        mockMvc.perform(delete("/api/v1/actors/{id}", ACTOR_ID))
                .andExpect(status().isNoContent());

        verify(actorService).deleteActor(ACTOR_ID);
    }

    @Test
    void shouldThrowOnDeleteIfActorNotFound() throws Exception {
        String message = "Actor with id " + ACTOR_ID + " not found";
        doThrow(new ResourceNotFoundException(message)).when(actorService).deleteActor(ACTOR_ID);

        mockMvc.perform(delete("/api/v1/actors/{id}", ACTOR_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.code").value(ErrorCode.NOT_FOUND.name()));

        verify(actorService).deleteActor(ACTOR_ID);
    }
}
