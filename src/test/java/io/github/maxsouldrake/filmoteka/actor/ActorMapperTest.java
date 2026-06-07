package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static io.github.maxsouldrake.filmoteka.actor.ActorTestData.*;
import static org.assertj.core.api.Assertions.assertThat;


class ActorMapperTest {
    private final ActorMapper actorMapper = Mappers.getMapper(ActorMapper.class);


    @Test
    void shouldMapActorRequestToActor() {
        Actor actor = actorMapper.actorRequestToActor(actorRequest());

        assertThat(actor.getName()).isEqualTo(ACTOR_NAME);
    }


    @Test
    void shouldMapActorToActorResponse() {
        ActorResponse response = actorMapper.actorToActorResponse(loadedActor());

        assertThat(response).isEqualTo(actorResponse());
    }

    @Test
    void shouldMapUpdateActorRequestToActor() {
        Actor actor = loadedActor();

        actorMapper.updateActorRequestToActor(updateActorRequest(), actor);

        assertThat(actor.getName()).isEqualTo("updated name");
    }
}