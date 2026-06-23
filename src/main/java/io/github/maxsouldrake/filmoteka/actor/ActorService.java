package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import io.github.maxsouldrake.filmoteka.actor.dto.DetailedActorResponse;
import io.github.maxsouldrake.filmoteka.common.exception.ConflictException;
import io.github.maxsouldrake.filmoteka.common.exception.ResourceNotFoundException;
import io.github.maxsouldrake.filmoteka.film.Film;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public DetailedActorResponse findById(Long id) {
        Actor actor = getActorOrThrow(id);
        return actorMapper.actorToDetailedActorResponse(actor);
    }

    @Transactional
    public Actor findOrCreate(ActorRequest request) {
        Optional<Actor> actor = actorRepository.findByName(request.name());
        if (actor.isPresent()) {
            return actor.get();
        }
        Actor saved = actorRepository.save(actorMapper.actorRequestToActor(request));
        log.info("Created actor id={}, name={}", saved.getId(), saved.getName());

        return saved;
    }

    @Transactional
    public DetailedActorResponse updateActor(Long id, ActorRequest request) {
        Actor actor = getActorOrThrow(id);
        if (!actor.getName().equals(request.name()) && actorRepository.existsByName(request.name())) {
            throw new ConflictException("Actor with name " + request.name() + " already exists");
        }
        actorMapper.updateActorRequestToActor(request, actor);

        Actor saved = actorRepository.save(actor);
        log.info("Updated actor id={} with name={}", saved.getId(), saved.getName());

        return actorMapper.actorToDetailedActorResponse(saved);
    }

    @Transactional
    public void deleteActor(Long id) {
        Actor actor = getActorOrThrow(id);
        for (Film film : new ArrayList<>(actor.getFilms())) {
            film.removeActor(actor);
        }
        actorRepository.delete(actor);
        log.info("Deleted actor id={}", id);
    }

    private Actor getActorOrThrow(Long id) {
        return actorRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Actor with id " + id + " not found"));
    }
}
