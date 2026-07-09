package io.github.mksfilmoteka.backend.actor;

import io.github.mksfilmoteka.backend.actor.dto.ActorRequest;
import io.github.mksfilmoteka.backend.actor.dto.ActorResponse;
import io.github.mksfilmoteka.backend.actor.dto.DetailedActorResponse;
import io.github.mksfilmoteka.backend.film.FilmMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = FilmMapper.class)
public interface ActorMapper {
    Actor actorRequestToActor(ActorRequest request);

    void updateActorRequestToActor(ActorRequest request, @MappingTarget Actor actor);

    ActorResponse actorToActorResponse(Actor actor);

    DetailedActorResponse actorToDetailedActorResponse(Actor actor);
}
