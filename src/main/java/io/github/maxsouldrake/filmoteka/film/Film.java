package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.actor.Actor;
import io.github.maxsouldrake.filmoteka.common.BaseEntity;
import io.github.maxsouldrake.filmoteka.director.Director;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "film", uniqueConstraints = @UniqueConstraint(
        columnNames = {"title", "release_year"}
))
public class Film extends BaseEntity {

    private String title;

    @Column(name = "release_year")
    private Integer releaseYear;

    private String country;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "film_genres",
            joinColumns = @JoinColumn(name = "film_id")
    )
    @Column(name = "genre")
    private List<Genre> genres = new ArrayList<>();

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "poster_url")
    private String posterUrl;

    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "film_director",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private List<Director> directors = new ArrayList<>();

    public void addActor(Actor actor) {
        actors.add(actor);
        actor.getFilms().add(this);
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
        actor.getFilms().remove(this);
    }

    public void addDirector(Director director) {
        directors.add(director);
        director.getFilms().add(this);
    }

    public void removeDirector(Director director) {
        directors.remove(director);
        director.getFilms().remove(this);
    }
}
