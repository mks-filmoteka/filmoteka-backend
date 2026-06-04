package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        ActorRequest request = new ActorRequest("test name");
        Actor existingActor = Actor.builder().id(1L).name("test name").build();
        when(actorRepository.findByName("test name")).thenReturn(Optional.of(existingActor));
        Actor actor = actorService.findOrCreate(request);
        assertThat(actor).isEqualTo(existingActor);
        verify(actorRepository, never()).save(any());
    }

    @Test
    void shouldCreateActorIfNotFound() {
        ActorRequest request = new ActorRequest("test name");
        when(actorRepository.findByName("test name")).thenReturn(Optional.empty());
        Actor mappedActor = Actor.builder().name("test name").build();
        when(actorMapper.actorRequestToActor(request)).thenReturn(mappedActor);
        when(actorRepository.save(mappedActor)).thenReturn(mappedActor);
        Actor actor = actorService.findOrCreate(request);
        assertThat(actor.getName()).isEqualTo("test name");
        verify(actorRepository).save(mappedActor);
    }
}