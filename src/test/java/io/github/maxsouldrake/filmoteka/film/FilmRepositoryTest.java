package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.config.RepositoryTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(RepositoryTestConfig.class)
class FilmRepositoryTest {

    @Autowired
    private FilmRepository filmRepository;

    @Test
    void shouldSaveAndLoadFilm() {
        Film film = Film.builder()
                .title("test title")
                .releaseYear(2000)
                .country("test country")
                .genres(Collections.emptySet())
                .build();

        Film savedFilm = filmRepository.save(film);
        Optional<Film> loadedFilm = filmRepository.findById(savedFilm.getId());

        assertNotNull(savedFilm.getId());
        assertTrue(loadedFilm.isPresent());
        assertEquals("test title", loadedFilm.get().getTitle());
        assertEquals(2000, loadedFilm.get().getReleaseYear());
        assertEquals("test country", loadedFilm.get().getCountry());
    }
}
