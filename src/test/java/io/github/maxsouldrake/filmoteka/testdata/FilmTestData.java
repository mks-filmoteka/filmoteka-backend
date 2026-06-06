package io.github.maxsouldrake.filmoteka.testdata;

import io.github.maxsouldrake.filmoteka.film.Film;
import io.github.maxsouldrake.filmoteka.film.Genre;
import io.github.maxsouldrake.filmoteka.film.dto.CreateFilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;

import java.util.Set;

import static io.github.maxsouldrake.filmoteka.testdata.ActorTestData.actorRequest;
import static io.github.maxsouldrake.filmoteka.testdata.ActorTestData.actorResponse;
import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.directorRequest;
import static io.github.maxsouldrake.filmoteka.testdata.DirectorTestData.directorResponse;

public class FilmTestData {

    public static final long FILM_ID = 1L;
    public static final String FILM_TITLE = "film title";
    public static final int RELEASE_YEAR = 2000;
    public static final String FILM_COUNTRY = "film country";
    public static final String FILM_DESCRIPTION = "film description";
    public static final String FILM_POSTER_URL = "http://film_poster";

    public static Film film() {
        return Film.builder()
                .title(FILM_TITLE)
                .releaseYear(RELEASE_YEAR)
                .country(FILM_COUNTRY)
                .description(FILM_DESCRIPTION)
                .posterUrl(FILM_POSTER_URL)
                .genres(Set.of(Genre.ADVENTURE, Genre.ACTION))
                .build();
    }

    public static Film loadedFilm() {
        return Film.builder()
                .id(FILM_ID)
                .title(FILM_TITLE)
                .releaseYear(RELEASE_YEAR)
                .country(FILM_COUNTRY)
                .description(FILM_DESCRIPTION)
                .posterUrl(FILM_POSTER_URL)
                .genres(Set.of(Genre.ADVENTURE, Genre.ACTION))
                .build();
    }

    public static CreateFilmRequest createFilmRequest() {
        return new CreateFilmRequest(
                FILM_TITLE,
                RELEASE_YEAR,
                FILM_COUNTRY,
                FILM_DESCRIPTION,
                FILM_POSTER_URL,
                Set.of(Genre.ADVENTURE, Genre.ACTION),
                null,
                null
        );
    }

    public static CreateFilmRequest createFilmRequestFull() {
        return new CreateFilmRequest(
                FILM_TITLE,
                RELEASE_YEAR,
                FILM_COUNTRY,
                FILM_DESCRIPTION,
                FILM_POSTER_URL,
                Set.of(Genre.ADVENTURE, Genre.ACTION),
                Set.of(actorRequest()),
                Set.of(directorRequest())
        );
    }

    public static DetailedFilmResponse detailedFilmResponse() {
        return new DetailedFilmResponse(
                FILM_ID,
                FILM_TITLE,
                RELEASE_YEAR,
                FILM_COUNTRY,
                FILM_DESCRIPTION,
                FILM_POSTER_URL,
                Set.of(Genre.ADVENTURE, Genre.ACTION),
                Set.of(actorResponse()),
                Set.of(directorResponse())
        );
    }
}
