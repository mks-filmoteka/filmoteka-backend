package io.github.maxsouldrake.filmoteka.director.entity;

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
@Table(name = "director")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "created_ts", updatable = false)
    private LocalDateTime createdTs;

    @Column(name = "updated_ts")
    private LocalDateTime updatedTs;

    @ManyToMany(mappedBy = "directors")
    private Set<Film> films = new HashSet<>();
}
