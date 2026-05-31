package io.github.maxsouldrake.filmoteka.actor.entity;

import io.github.maxsouldrake.filmoteka.film.entity.Film;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "actor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "created_ts", updatable = false)
    private LocalDateTime createdTs;

    @Column(name = "updated_ts")
    private LocalDateTime updatedTs;

    @ManyToMany(mappedBy = "actors")
    private Set<Film> films = new HashSet<>();
}
