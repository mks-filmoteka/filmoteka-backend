package io.github.maxsouldrake.filmoteka.testdata;

import io.github.maxsouldrake.filmoteka.actor.Actor;
import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import io.github.maxsouldrake.filmoteka.actor.dto.ActorResponse;
import io.github.maxsouldrake.filmoteka.actor.dto.DetailedActorResponse;

import java.util.Set;

import static io.github.maxsouldrake.filmoteka.testdata.FilmTestData.filmResponse;

public class ActorTestData {

    public static final String ACTOR_NAME = "actor name";
    public static final long ACTOR_ID = 1L;

    public static Actor actor() {
        return Actor.builder().name(ACTOR_NAME).build();
    }

    public static Actor loadedActor() {
        return Actor.builder().id(ACTOR_ID).name(ACTOR_NAME).build();
    }

    public static ActorRequest actorRequest() {
        return new ActorRequest(ACTOR_NAME);
    }

    public static ActorResponse actorResponse() {
        return new ActorResponse(ACTOR_ID, ACTOR_NAME);
    }

    public static DetailedActorResponse detailedActorResponse() {
        return new DetailedActorResponse(
                ACTOR_ID,
                ACTOR_NAME,
                Set.of(filmResponse())
        );
    }
}
