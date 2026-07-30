package io.github.mksfilmoteka.catalog.director;

import io.github.mksfilmoteka.catalog.config.RepositoryTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static io.github.mksfilmoteka.catalog.director.DirectorTestData.DIRECTOR_NAME;
import static io.github.mksfilmoteka.catalog.director.DirectorTestData.director;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(RepositoryTestConfig.class)
@Testcontainers(disabledWithoutDocker = true)
class DirectorRepositoryTest {

    @Autowired
    private DirectorRepository directorRepository;

    @Test
    void shouldSaveAndLoadDirector() {
        Director savedDirector = directorRepository.saveAndFlush(director());
        Optional<Director> loadedDirector = directorRepository.findById(savedDirector.getId());

        assertNotNull(savedDirector.getId());
        assertTrue(loadedDirector.isPresent());
        assertEquals(DIRECTOR_NAME, loadedDirector.get().getName());
    }

    @Test
    void shouldFindDirectorByName() {
        Director savedDirector = directorRepository.saveAndFlush(director());
        Optional<Director> loadedDirector = directorRepository.findByName(savedDirector.getName());

        assertTrue(loadedDirector.isPresent());
        assertEquals(DIRECTOR_NAME, loadedDirector.get().getName());
    }
}
