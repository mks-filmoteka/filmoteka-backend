package io.github.mksfilmoteka.backend.actor;

import io.github.mksfilmoteka.backend.common.BaseEntity;
import io.github.mksfilmoteka.backend.film.Film;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "actor", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Actor extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "actors")
    private List<Film> films = new ArrayList<>();
}
