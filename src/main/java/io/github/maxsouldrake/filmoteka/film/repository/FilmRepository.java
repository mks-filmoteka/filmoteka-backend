package io.github.maxsouldrake.filmoteka.film.repository;

import io.github.maxsouldrake.filmoteka.film.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
