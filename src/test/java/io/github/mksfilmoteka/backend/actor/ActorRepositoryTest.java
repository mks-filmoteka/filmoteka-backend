package io.github.mksfilmoteka.backend.actor;

import io.github.mksfilmoteka.backend.config.RepositoryTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static io.github.mksfilmoteka.backend.actor.ActorTestData.ACTOR_NAME;
import static io.github.mksfilmoteka.backend.actor.ActorTestData.actor;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(RepositoryTestConfig.class)
@Testcontainers(disabledWithoutDocker = true)
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
