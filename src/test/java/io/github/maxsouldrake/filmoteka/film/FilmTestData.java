package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmFilter;
import io.github.maxsouldrake.filmoteka.film.dto.FilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;

import static io.github.maxsouldrake.filmoteka.actor.ActorTestData.actorRequest;
import static io.github.maxsouldrake.filmoteka.actor.ActorTestData.actorResponse;
import static io.github.maxsouldrake.filmoteka.director.DirectorTestData.directorRequest;
import static io.github.maxsouldrake.filmoteka.director.DirectorTestData.directorResponse;
import static io.github.maxsouldrake.filmoteka.util.TestUtil.testListOf;
import static io.github.maxsouldrake.filmoteka.util.TestUtil.testSetOf;

public class FilmTestData {

    public static final long FILM_ID = 1L;
    public static final String FILM_TITLE = "film title";
    public static final int RELEASE_YEAR = 2000;
    public static final String FILM_DESCRIPTION = "film description";
    public static final String FILM_POSTER_URL = "http://film_poster";

    public static Film film() {
        Film film = new Film();
        film.setTitle(FILM_TITLE);
        film.setReleaseYear(RELEASE_YEAR);
        film.setCountries(testListOf(Country.UNITED_STATES, Country.ITALY));
        film.setDescription(FILM_DESCRIPTION);
        film.setPosterUrl(FILM_POSTER_URL);
        film.setGenres(testListOf(Genre.ADVENTURE, Genre.ACTION));
        return film;
    }

    public static Film loadedFilm() {
        Film film = film();
        film.setId(FILM_ID);
        return film;
    }

    public static FilmRequest filmRequest() {
        return new FilmRequest(
                FILM_TITLE,
                RELEASE_YEAR,
                testListOf(Country.UNITED_STATES, Country.ITALY),
                FILM_DESCRIPTION,
                FILM_POSTER_URL,
                testListOf(Genre.ADVENTURE, Genre.ACTION),
                null,
                null
        );
    }

    public static FilmRequest invalidFilmRequest() {
        return new FilmRequest(
                "",
                1700,
                testListOf(),
                "",
                "url",
                testListOf(),
                testListOf(),
                testListOf()
        );
    }

    public static FilmRequest filmRequestFull() {
        return new FilmRequest(
                FILM_TITLE,
                RELEASE_YEAR,
                testListOf(Country.UNITED_STATES, Country.ITALY),
                FILM_DESCRIPTION,
                FILM_POSTER_URL,
                testListOf(Genre.ADVENTURE, Genre.ACTION),
                testListOf(actorRequest()),
                testListOf(directorRequest())
        );
    }

    public static FilmRequest updateFilmRequest() {
        return new FilmRequest(
                "updated title",
                1999,
                testListOf(Country.CANADA),
                FILM_DESCRIPTION,
                FILM_POSTER_URL,
                testListOf(Genre.COMEDY),
                null,
                null
        );
    }

    public static DetailedFilmResponse detailedFilmResponse() {
        return new DetailedFilmResponse(
                FILM_ID,
                FILM_TITLE,
                RELEASE_YEAR,
                testListOf(Country.UNITED_STATES, Country.ITALY),
                FILM_DESCRIPTION,
                FILM_POSTER_URL,
                testListOf(Genre.ADVENTURE, Genre.ACTION),
                testListOf(),
                testListOf()
        );
    }

    public static DetailedFilmResponse detailedFilmResponseFull() {
        return new DetailedFilmResponse(
                FILM_ID,
                FILM_TITLE,
                RELEASE_YEAR,
                testListOf(Country.UNITED_STATES, Country.ITALY),
                FILM_DESCRIPTION,
                FILM_POSTER_URL,
                testListOf(Genre.ADVENTURE, Genre.ACTION),
                testListOf(actorResponse()),
                testListOf(directorResponse())
        );
    }

    public static FilmResponse filmResponse() {
        return new FilmResponse(
                FILM_ID,
                FILM_TITLE,
                testListOf(Country.UNITED_STATES, Country.ITALY),
                RELEASE_YEAR,
                FILM_POSTER_URL,
                testListOf(Genre.ADVENTURE, Genre.ACTION)
        );
    }

    public static FilmFilter filmFilter() {
        return new FilmFilter(
                FILM_TITLE,
                RELEASE_YEAR,
                RELEASE_YEAR + 10,
                testSetOf(Genre.ADVENTURE, Genre.ACTION),
                testSetOf(Country.UNITED_STATES, Country.ITALY));
    }

    public static FilmFilter emptyFilmFilter() {
        return new FilmFilter(null, null, null, null, null);
    }
}
