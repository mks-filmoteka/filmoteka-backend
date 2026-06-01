package io.github.maxsouldrake.filmoteka.film.repository;

import io.github.maxsouldrake.filmoteka.film.entity.Film;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmRepositoryTest {

    @Autowired
    private FilmRepository filmRepository;

    @Test
    void shouldSaveAndLoadFilm() {
        Film film = new Film("test title", 2000, "test country");

        Film savedFilm = filmRepository.save(film);
        Optional<Film> loadedFilm = filmRepository.findById(savedFilm.getId());

        assertNotNull(savedFilm.getId());
        assertTrue(loadedFilm.isPresent());
        assertEquals("test title", loadedFilm.get().getTitle());
        assertEquals(2000, loadedFilm.get().getReleaseYear());
        assertEquals("test country", loadedFilm.get().getCountry());
    }
}
