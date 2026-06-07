package io.github.maxsouldrake.filmoteka.director;

import io.github.maxsouldrake.filmoteka.director.dto.DetailedDirectorResponse;
import io.github.maxsouldrake.filmoteka.director.dto.DirectorRequest;
import io.github.maxsouldrake.filmoteka.director.dto.DirectorResponse;
import io.github.maxsouldrake.filmoteka.film.FilmMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = FilmMapper.class)
public interface DirectorMapper {
    Director directorRequestToDirector(DirectorRequest request);
    void updateDirectorRequestToDirector(DirectorRequest request, @MappingTarget Director director);
    DirectorResponse directorToDirectorResponse(Director director);
    DetailedDirectorResponse directorToDetailedDirectorResponse(Director director);
}
