package io.github.mksfilmoteka.catalog.director;

import io.github.mksfilmoteka.catalog.director.dto.DetailedDirectorResponse;
import io.github.mksfilmoteka.catalog.director.dto.DirectorRequest;
import io.github.mksfilmoteka.catalog.director.dto.DirectorResponse;
import io.github.mksfilmoteka.catalog.film.FilmMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = FilmMapper.class)
public interface DirectorMapper {
    Director directorRequestToDirector(DirectorRequest request);

    void updateDirectorRequestToDirector(DirectorRequest request, @MappingTarget Director director);

    DirectorResponse directorToDirectorResponse(Director director);

    DetailedDirectorResponse directorToDetailedDirectorResponse(Director director);
}
