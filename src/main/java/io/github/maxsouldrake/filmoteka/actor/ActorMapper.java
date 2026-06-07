package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import io.github.maxsouldrake.filmoteka.actor.dto.ActorResponse;
import io.github.maxsouldrake.filmoteka.actor.dto.DetailedActorResponse;
import io.github.maxsouldrake.filmoteka.film.FilmMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = FilmMapper.class)
public interface ActorMapper {
    Actor actorRequestToActor(ActorRequest request);
    void updateActorRequestToActor(ActorRequest request, @MappingTarget Actor actor);
    ActorResponse actorToActorResponse(Actor actor);
    DetailedActorResponse actorToDetailedActorResponse(Actor actor);
}
