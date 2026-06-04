package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.config.RepositoryTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
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
                .description("test description")
                .posterUrl("http://test")
                .genres(Set.of(Genre.ADVENTURE, Genre.ACTION))
                .build();

        Film savedFilm = filmRepository.saveAndFlush(film);
        Optional<Film> loadedFilm = filmRepository.findById(savedFilm.getId());

        assertNotNull(savedFilm.getId());
        assertTrue(loadedFilm.isPresent());
        assertEquals("test title", loadedFilm.get().getTitle());
        assertEquals(2000, loadedFilm.get().getReleaseYear());
        assertEquals("test country", loadedFilm.get().getCountry());
        assertEquals("test description", loadedFilm.get().getDescription());
        assertEquals("http://test", loadedFilm.get().getPosterUrl());
        assertThat(loadedFilm.get().getGenres()).containsExactlyInAnyOrder(Genre.ADVENTURE, Genre.ACTION);
    }
}
