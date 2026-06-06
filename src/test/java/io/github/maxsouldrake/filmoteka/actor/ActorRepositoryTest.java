package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.config.RepositoryTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static io.github.maxsouldrake.filmoteka.testdata.ActorTestData.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(RepositoryTestConfig.class)
class ActorRepositoryTest {

    @Autowired
    private ActorRepository actorRepository;

    @Test
    void shouldSaveAndLoadActor() {
        Actor savedActor = actorRepository.saveAndFlush(actor());
        Optional<Actor> loadedActor = actorRepository.findById(savedActor.getId());

        assertNotNull(savedActor.getId());
        assertTrue(loadedActor.isPresent());
        assertEquals(ACTOR_NAME, loadedActor.get().getName());
    }

    @Test
    void shouldFindActorByName() {
        Actor savedActor = actorRepository.saveAndFlush(actor());
        Optional<Actor> loadedActor = actorRepository.findByName(savedActor.getName());

        assertTrue(loadedActor.isPresent());
        assertEquals(ACTOR_NAME, loadedActor.get().getName());
    }
}