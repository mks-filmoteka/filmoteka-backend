package io.github.maxsouldrake.filmoteka.actor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    Optional<Actor> findByName(String name);
    boolean existsByName(String name);
}
