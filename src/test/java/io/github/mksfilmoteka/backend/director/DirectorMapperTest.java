package io.github.mksfilmoteka.backend.director;

import io.github.mksfilmoteka.backend.director.dto.DetailedDirectorResponse;
import io.github.mksfilmoteka.backend.director.dto.DirectorResponse;
import io.github.mksfilmoteka.backend.film.FilmMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;

import static io.github.mksfilmoteka.backend.director.DirectorTestData.*;
import static io.github.mksfilmoteka.backend.film.FilmTestData.loadedFilm;
import static org.assertj.core.api.Assertions.assertThat;

class DirectorMapperTest {
    private final DirectorMapper directorMapper = Mappers.getMapper(DirectorMapper.class);

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(directorMapper, "filmMapper", Mappers.getMapper(FilmMapper.class));
    }

    @Test
    void shouldMapDirectorRequestToDirector() {
        Director director = directorMapper.directorRequestToDirector(directorRequest());

        assertThat(director.getName()).isEqualTo(DIRECTOR_NAME);
    }

    @Test
    void shouldMapDirectorToDirectorResponse() {
        DirectorResponse response = directorMapper.directorToDirectorResponse(loadedDirector());

        assertThat(response).isEqualTo(directorResponse());
    }

    @Test
    void shouldMapDirectorToDetailedDirectorResponse() {
        Director director = loadedDirector();
        director.getFilms().add(loadedFilm());

        DetailedDirectorResponse response = directorMapper.directorToDetailedDirectorResponse(director);

        assertThat(response).isEqualTo(detailedDirectorResponse());
    }

    @Test
    void shouldMapUpdateDirectorRequestToDirector() {
        Director director = loadedDirector();

        directorMapper.updateDirectorRequestToDirector(updateDirectorRequest(), director);

        assertThat(director.getName()).isEqualTo("updated name");
    }
}
