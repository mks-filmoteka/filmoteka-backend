package io.github.maxsouldrake.filmoteka.director;

import io.github.maxsouldrake.filmoteka.director.dto.DirectorRequest;
import io.github.maxsouldrake.filmoteka.director.dto.DirectorResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class DirectorMapperTest {
    private final DirectorMapper directorMapper = Mappers.getMapper(DirectorMapper.class);


    @Test
    void shouldMapDirectorRequestToDirector() {
        DirectorRequest request = new DirectorRequest("test name");

        Director director = directorMapper.directorRequestToDirector(request);

        assertThat(director.getName()).isEqualTo("test name");
    }


    @Test
    void shouldMapDirectorToDirectorResponse() {
        Director director = Director.builder().id(1L).name("test name").build();

        DirectorResponse response = directorMapper.directorToDirectorResponse(director);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("test name");
    }
}