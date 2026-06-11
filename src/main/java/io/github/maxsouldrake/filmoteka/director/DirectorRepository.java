package io.github.maxsouldrake.filmoteka.director;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, Long> {
    Optional<Director> findByName(String name);
    boolean existsByName(String name);
}
