package io.github.mksfilmoteka.backend.director;

import io.github.mksfilmoteka.backend.director.dto.DirectorResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static io.github.mksfilmoteka.backend.director.DirectorTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class DirectorMapperTest {
    private final DirectorMapper directorMapper = Mappers.getMapper(DirectorMapper.class);

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
    void shouldMapUpdateDirectorRequestToDirector() {
        Director director = loadedDirector();

        directorMapper.updateDirectorRequestToDirector(updateDirectorRequest(), director);

        assertThat(director.getName()).isEqualTo("updated name");
    }
}