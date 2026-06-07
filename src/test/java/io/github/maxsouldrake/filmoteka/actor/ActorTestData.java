package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import io.github.maxsouldrake.filmoteka.actor.dto.ActorResponse;
import io.github.maxsouldrake.filmoteka.actor.dto.DetailedActorResponse;
import io.github.maxsouldrake.filmoteka.util.TestUtil;

import static io.github.maxsouldrake.filmoteka.film.FilmTestData.filmResponse;

public class ActorTestData {

    public static final String ACTOR_NAME = "actor name";
    public static final long ACTOR_ID = 1L;

    public static Actor actor() {
        Actor actor = new Actor();
        actor.setName(ACTOR_NAME);
        return actor;
    }

    public static Actor loadedActor() {
        Actor actor = actor();
        actor.setId(ACTOR_ID);
        return actor;
    }

    public static ActorRequest actorRequest() {
        return new ActorRequest(ACTOR_NAME);
    }

    public static ActorRequest updateActorRequest() {
        return new ActorRequest("updated name");
    }

    public static ActorResponse actorResponse() {
        return new ActorResponse(ACTOR_ID, ACTOR_NAME);
    }

    public static DetailedActorResponse detailedActorResponse() {
        return new DetailedActorResponse(
                ACTOR_ID,
                ACTOR_NAME,
                TestUtil.testSetOf(filmResponse())
        );
    }
}
