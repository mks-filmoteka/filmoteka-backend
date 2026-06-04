package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.config.RepositoryTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(RepositoryTestConfig.class)
class ActorRepositoryTest {

    @Autowired
    private ActorRepository actorRepository;

    @BeforeEach
    void setup() {
        Actor actor1 = Actor.builder().name("test name1").build();
        actorRepository.save(actor1);
        Actor actor2 = Actor.builder().name("test name2").build();
        actorRepository.save(actor2);
    }

    @Test
    void shouldSaveAndLoadActor() {
        Actor actor = Actor.builder()
                .name("test name")
                .build();

        Actor savedActor = actorRepository.saveAndFlush(actor);
        Optional<Actor> loadedActor = actorRepository.findById(savedActor.getId());

        assertNotNull(savedActor.getId());
        assertTrue(loadedActor.isPresent());
        assertEquals("test name", loadedActor.get().getName());
    }

    @Test
    void shouldFindActorByName() {
        Optional<Actor> loadedActor = actorRepository.findByName("test name1");

        assertTrue(loadedActor.isPresent());
        assertNotNull(loadedActor.get().getId());
        assertEquals("test name1", loadedActor.get().getName());
    }
}