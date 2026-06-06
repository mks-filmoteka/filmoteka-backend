package io.github.maxsouldrake.filmoteka.director;

import io.github.maxsouldrake.filmoteka.config.RepositoryTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.DIRECTOR_NAME;
import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.director;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(RepositoryTestConfig.class)
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