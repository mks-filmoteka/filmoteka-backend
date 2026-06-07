package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import io.github.maxsouldrake.filmoteka.actor.dto.DetailedActorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.NoSuchElementException;
import java.util.Optional;

import static io.github.maxsouldrake.filmoteka.actor.ActorTestData.*;
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

    @Test
    void shouldUpdateActorIfExists() {
        Actor loadedActor = loadedActor();
        loadedActor.setName("old name");

        when(actorRepository.findById(ACTOR_ID)).thenReturn(Optional.of(loadedActor));

        doAnswer(updateNameOnly()).when(actorMapper).updateActorRequestToActor(any(), any());

        when(actorRepository.save(any(Actor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(actorMapper.actorToDetailedActorResponse(any(Actor.class))).thenReturn(detailedActorResponse());

        DetailedActorResponse response = actorService.updateActor(ACTOR_ID, actorRequest());

        assertThat(response).isEqualTo(detailedActorResponse());
        ArgumentCaptor<Actor> captor = ArgumentCaptor.forClass(Actor.class);

        verify(actorRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo(ACTOR_NAME);
        verify(actorMapper).updateActorRequestToActor(actorRequest(), loadedActor);
        verify(actorMapper).actorToDetailedActorResponse(any(Actor.class));
    }

    private static Answer<Void> updateNameOnly() {
        return invocation -> {
            ActorRequest request = invocation.getArgument(0);
            Actor actor = invocation.getArgument(1);
            actor.setName(request.name());
            return null;
        };
    }
}