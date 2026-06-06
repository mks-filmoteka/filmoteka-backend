package io.github.maxsouldrake.filmoteka.testdata;

import io.github.maxsouldrake.filmoteka.director.Director;
import io.github.maxsouldrake.filmoteka.director.dto.DetailedDirectorResponse;
import io.github.maxsouldrake.filmoteka.director.dto.DirectorRequest;
import io.github.maxsouldrake.filmoteka.director.dto.DirectorResponse;

import java.util.Set;

import static io.github.maxsouldrake.filmoteka.testdata.FilmTestData.filmResponse;

public class DirectorTestData {

    public static final String DIRECTOR_NAME = "director name";
    public static final long DIRECTOR_ID = 1L;

    public static Director director() {
        return Director.builder().name(DIRECTOR_NAME).build();
    }

    public static Director loadedDirector() {
        return Director.builder().id(DIRECTOR_ID).name(DIRECTOR_NAME).build();
    }

    public static DirectorRequest directorRequest() {
        return new DirectorRequest(DIRECTOR_NAME);
    }

    public static DirectorResponse directorResponse() {
        return new DirectorResponse(DIRECTOR_ID, DIRECTOR_NAME);
    }

    public static DetailedDirectorResponse detailedDirectorResponse() {
        return new DetailedDirectorResponse(
                DIRECTOR_ID,
                DIRECTOR_NAME,
                Set.of(filmResponse())
        );
    }
}
