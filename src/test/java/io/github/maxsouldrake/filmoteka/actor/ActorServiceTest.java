package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.actor.dto.DetailedActorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static io.github.maxsouldrake.filmoteka.testdata.ActorTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock ActorMapper actorMapper;

    @InjectMocks
    private ActorService actorService;

    @Test
    void shouldNotCreateActorIfFound() {
        Actor loadedActor = loadedActor();
        when(actorRepository.findByName(ACTOR_NAME)).thenReturn(Optional.of(loadedActor));

        Actor actor = actorService.findOrCreate(actorRequest());
        assertThat(actor).isEqualTo(loadedActor);
        verify(actorRepository, never()).save(any());
    }

    @Test
    void shouldCreateActorIfNotFound() {
        Actor actor = actor();
        when(actorRepository.findByName(ACTOR_NAME)).thenReturn(Optional.empty());
        when(actorMapper.actorRequestToActor(actorRequest())).thenReturn(actor);
        when(actorRepository.save(actor)).thenReturn(loadedActor());

        Actor loadedActor = actorService.findOrCreate(actorRequest());
        assertThat(loadedActor.getName()).isEqualTo(ACTOR_NAME);
        verify(actorRepository).save(actor);
    }

    @Test
    void shouldFindActorByIdIfExists() {
        Actor loadedActor = loadedActor();

        when(actorRepository.findById(ACTOR_ID)).thenReturn(Optional.of(loadedActor));
        when(actorMapper.actorToDetailedActorResponse(loadedActor)).thenReturn(detailedActorResponse());

        DetailedActorResponse response = actorService.findById(ACTOR_ID);

        assertThat(response).isEqualTo(detailedActorResponse());
        verify(actorRepository).findById(ACTOR_ID);
        verify(actorMapper).actorToDetailedActorResponse(loadedActor);
    }

    @Test
    void shouldThrowIfDoesNotExist() {
        when(actorRepository.findById(ACTOR_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> actorService.findById(ACTOR_ID));

        verify(actorRepository).findById(ACTOR_ID);
        verifyNoInteractions(actorMapper);
    }
}