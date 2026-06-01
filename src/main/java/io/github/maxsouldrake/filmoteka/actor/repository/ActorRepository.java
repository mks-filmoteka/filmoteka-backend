package io.github.maxsouldrake.filmoteka.actor.repository;

import io.github.maxsouldrake.filmoteka.actor.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
