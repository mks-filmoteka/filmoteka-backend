package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.config.RepositoryTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static io.github.maxsouldrake.filmoteka.testdata.FilmTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(RepositoryTestConfig.class)
class FilmRepositoryTest {

    @Autowired
    private FilmRepository filmRepository;

    @Test
    void shouldSaveAndLoadFilm() {
        Film savedFilm = filmRepository.saveAndFlush(film());
        Optional<Film> loadedFilm = filmRepository.findById(savedFilm.getId());

        assertNotNull(savedFilm.getId());
        assertTrue(loadedFilm.isPresent());
        assertEquals(FILM_TITLE, loadedFilm.get().getTitle());
        assertEquals(RELEASE_YEAR, loadedFilm.get().getReleaseYear());
        assertEquals(FILM_COUNTRY, loadedFilm.get().getCountry());
        assertEquals(FILM_DESCRIPTION, loadedFilm.get().getDescription());
        assertEquals(FILM_POSTER_URL, loadedFilm.get().getPosterUrl());
        assertThat(loadedFilm.get().getGenres()).containsExactlyInAnyOrder(Genre.ADVENTURE, Genre.ACTION);
    }
}
