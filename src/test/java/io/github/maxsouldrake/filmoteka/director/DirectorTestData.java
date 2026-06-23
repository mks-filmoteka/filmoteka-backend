package io.github.maxsouldrake.filmoteka.director;

import io.github.maxsouldrake.filmoteka.director.dto.DetailedDirectorResponse;
import io.github.maxsouldrake.filmoteka.director.dto.DirectorRequest;
import io.github.maxsouldrake.filmoteka.director.dto.DirectorResponse;
import io.github.maxsouldrake.filmoteka.util.TestUtil;

import static io.github.maxsouldrake.filmoteka.film.FilmTestData.filmResponse;

public class DirectorTestData {

    public static final String DIRECTOR_NAME = "director name";
    public static final long DIRECTOR_ID = 1L;

    public static Director director() {
        Director director = new Director();
        director.setName(DIRECTOR_NAME);
        return director;
    }

    public static Director loadedDirector() {
        Director director = director();
        director.setId(DIRECTOR_ID);
        return director;
    }

    public static DirectorRequest directorRequest() {
        return new DirectorRequest(DIRECTOR_NAME);
    }

    public static DirectorRequest updateDirectorRequest() {
        return new DirectorRequest("updated name");
    }

    public static DirectorResponse directorResponse() {
        return new DirectorResponse(DIRECTOR_ID, DIRECTOR_NAME);
    }

    public static DetailedDirectorResponse detailedDirectorResponse() {
        return new DetailedDirectorResponse(
                DIRECTOR_ID,
                DIRECTOR_NAME,
                TestUtil.testListOf(filmResponse())
        );
    }
}
