package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.FilmFilter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FilmSpecification {

    public static Specification<Film> withFilters(FilmFilter filter) {
        return Specification.where(hasTitle(filter.title()))
                .and(hasCountry(filter.country()))
                .and(hasReleaseYearFrom(filter.yearFrom()))
                .and(hasReleaseYearTo(filter.yearTo()))
                .and(hasGenres(filter.genres()));
    }

    public static Specification<Film> hasTitle(String title) {

        return (root, query, cb) ->
                title == null || title.isBlank()
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Film> hasCountry(String country) {

        return (root, query, cb) ->
                country == null || country.isBlank()
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("country")), country.toLowerCase());
    }

    public static Specification<Film> hasReleaseYearFrom(Integer releaseYearFrom) {

        return (root, query, cb) ->
            releaseYearFrom == null
                    ? cb.conjunction()
                    : cb.greaterThanOrEqualTo(root.get("releaseYear"), releaseYearFrom);
    }

    public static Specification<Film> hasReleaseYearTo(Integer releaseYearTo) {

        return (root, query, cb) ->
                releaseYearTo == null
                        ? cb.conjunction()
                        : cb.lessThanOrEqualTo(root.get("releaseYear"), releaseYearTo);
    }

    public static Specification<Film> hasGenres(Set<Genre> genres) {

        return (root, query, cb) -> {
            query.distinct(true);
            return genres == null || genres.isEmpty()
                    ? cb.conjunction()
                    :  root.join("genres").in(genres);
        };
    }
}
