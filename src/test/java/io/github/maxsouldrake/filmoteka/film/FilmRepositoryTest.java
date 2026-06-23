package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.config.RepositoryTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static io.github.maxsouldrake.filmoteka.film.FilmTestData.*;
import static io.github.maxsouldrake.filmoteka.util.TestUtil.testListOf;
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

    @Test
    void shouldFindPagedFilmsByFilter() {
        Film nonComplFilm = film();
        nonComplFilm.setTitle("different title");
        nonComplFilm.setReleaseYear(1999);
        nonComplFilm.setCountry("different country");
        nonComplFilm.setGenres(testListOf(Genre.CRIME));
        filmRepository.saveAndFlush(nonComplFilm);
        filmRepository.saveAndFlush(film());

        Pageable pageable = PageRequest.of(0, 100);
        Page<Film> page = filmRepository.findAll(FilmSpecification.withFilters(filmFilter()), pageable);

        assertThat(page.getContent()).extracting(Film::getTitle).containsExactlyInAnyOrder(FILM_TITLE);
        assertThat(page.getContent()).extracting(Film::getReleaseYear).containsExactlyInAnyOrder(RELEASE_YEAR);
        assertThat(page.getContent()).extracting(Film::getCountry).containsExactlyInAnyOrder(FILM_COUNTRY);
        assertThat(page.getContent().getFirst().getGenres()).containsExactlyInAnyOrder(Genre.ADVENTURE, Genre.ACTION);
    }

    @Test
    void shouldReturnFilmsSortedByTitleAscending() {
        Film filmA = film();
        filmA.setTitle("A");
        Film filmB = film();
        filmB.setTitle("B");
        Film filmC = film();
        filmC.setTitle("C");

        filmRepository.saveAndFlush(filmC);
        filmRepository.saveAndFlush(filmB);
        filmRepository.saveAndFlush(filmA);

        Page<Film> page = filmRepository.findAll(FilmSpecification.hasTitle(null),
                PageRequest.of(0, 100, Sort.by("title").ascending()));

        assertThat(page.getContent())
                .extracting(Film::getTitle)
                .containsExactly("A", "B", "C");
    }
}
