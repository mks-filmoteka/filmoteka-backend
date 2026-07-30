package io.github.mksfilmoteka.catalog.actor;

import io.github.mksfilmoteka.catalog.actor.dto.ActorResponse;
import io.github.mksfilmoteka.catalog.actor.dto.DetailedActorResponse;
import io.github.mksfilmoteka.catalog.film.FilmMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;

import static io.github.mksfilmoteka.catalog.actor.ActorTestData.*;
import static io.github.mksfilmoteka.catalog.film.FilmTestData.loadedFilm;
import static org.assertj.core.api.Assertions.assertThat;

class ActorMapperTest {
    private final ActorMapper actorMapper = Mappers.getMapper(ActorMapper.class);

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(actorMapper, "filmMapper", Mappers.getMapper(FilmMapper.class));
    }

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
    void shouldMapActorToDetailedActorResponse() {
        Actor actor = loadedActor();
        actor.getFilms().add(loadedFilm());

        DetailedActorResponse response = actorMapper.actorToDetailedActorResponse(actor);

        assertThat(response).isEqualTo(detailedActorResponse());
    }

    @Test
    void shouldMapUpdateActorRequestToActor() {
        Actor actor = loadedActor();

        actorMapper.updateActorRequestToActor(updateActorRequest(), actor);

        assertThat(actor.getName()).isEqualTo("updated name");
    }
}
