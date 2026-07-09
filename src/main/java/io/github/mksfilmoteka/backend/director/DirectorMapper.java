package io.github.mksfilmoteka.backend.director;

import io.github.mksfilmoteka.backend.director.dto.DetailedDirectorResponse;
import io.github.mksfilmoteka.backend.director.dto.DirectorRequest;
import io.github.mksfilmoteka.backend.director.dto.DirectorResponse;
import io.github.mksfilmoteka.backend.film.FilmMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = FilmMapper.class)
public interface DirectorMapper {
    Director directorRequestToDirector(DirectorRequest request);

    void updateDirectorRequestToDirector(DirectorRequest request, @MappingTarget Director director);

    DirectorResponse directorToDirectorResponse(Director director);

    DetailedDirectorResponse directorToDetailedDirectorResponse(Director director);
}
