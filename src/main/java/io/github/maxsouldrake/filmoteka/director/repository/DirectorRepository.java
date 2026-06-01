package io.github.maxsouldrake.filmoteka.director.repository;

import io.github.maxsouldrake.filmoteka.director.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}
