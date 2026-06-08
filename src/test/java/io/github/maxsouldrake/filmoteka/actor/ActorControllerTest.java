package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static io.github.maxsouldrake.filmoteka.actor.ActorTestData.*;
import static io.github.maxsouldrake.filmoteka.util.TestUtil.OBJECT_MAPPER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        when(actorService.findById(ACTOR_ID)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/actors/{id}", ACTOR_ID)).andExpect(status().isNotFound());
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
    void shouldDeleteActor() throws Exception {

        mockMvc.perform(delete("/api/v1/actors/{id}", ACTOR_ID))
                .andExpect(status().isNoContent());

        verify(actorService).deleteActor(ACTOR_ID);
    }
}