package io.github.maxsouldrake.filmoteka.film.entity;

import io.github.maxsouldrake.filmoteka.actor.entity.Actor;
import io.github.maxsouldrake.filmoteka.director.entity.Director;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "film")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "release_year")
    private Integer releaseYear;

    private String country;

    @Enumerated(EnumType.STRING)
    private Set<Genre> genres = new HashSet<>();

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "created_ts", updatable = false)
    private LocalDateTime createdTs;

    @Column(name = "updated_ts")
    private LocalDateTime updatedTs;

    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "film_director",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directors = new HashSet<>();

    @PreUpdate
    public void preUpdate() {
        updatedTs = LocalDateTime.now();
    }
}
