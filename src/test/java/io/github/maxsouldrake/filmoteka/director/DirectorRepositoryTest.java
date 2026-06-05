package io.github.maxsouldrake.filmoteka.director;

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
class DirectorRepositoryTest {

    @Autowired
    private DirectorRepository directorRepository;

    @BeforeEach
    void setup() {
        Director director1 = Director.builder().name("test name1").build();
        directorRepository.save(director1);
        Director director2 = Director.builder().name("test name2").build();
        directorRepository.save(director2);
    }

    @Test
    void shouldSaveAndLoadDirector() {
        Director director = Director.builder()
                .name("test name")
                .build();

        Director savedDirector = directorRepository.saveAndFlush(director);
        Optional<Director> loadedDirector = directorRepository.findById(savedDirector.getId());

        assertNotNull(savedDirector.getId());
        assertTrue(loadedDirector.isPresent());
        assertEquals("test name", loadedDirector.get().getName());
    }

    @Test
    void shouldFindDirectorByName() {
        Optional<Director> loadedDirector = directorRepository.findByName("test name1");

        assertTrue(loadedDirector.isPresent());
        assertNotNull(loadedDirector.get().getId());
        assertEquals("test name1", loadedDirector.get().getName());
    }
}