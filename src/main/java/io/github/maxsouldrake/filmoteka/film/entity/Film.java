package io.github.maxsouldrake.filmoteka.film.entity;

import io.github.maxsouldrake.filmoteka.actor.entity.Actor;
import io.github.maxsouldrake.filmoteka.director.entity.Director;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "film")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    @Column(name = "release_year")
    private Integer releaseYear;

    @NonNull
    private String country;

    @Enumerated(EnumType.STRING)
    private Set<Genre> genres = new HashSet<>();

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "poster_url")
    private String posterUrl;

    @CreationTimestamp
    @Column(name = "created_ts", updatable = false)
    private LocalDateTime createdTs;

    @UpdateTimestamp
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
