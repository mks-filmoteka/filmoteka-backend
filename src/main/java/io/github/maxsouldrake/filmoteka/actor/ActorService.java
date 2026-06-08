package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import io.github.maxsouldrake.filmoteka.actor.dto.DetailedActorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public DetailedActorResponse findById(Long id) {
        Actor actor = actorRepository.findById(id).orElseThrow();
        return actorMapper.actorToDetailedActorResponse(actor);
    }

    @Transactional
    public Actor findOrCreate(ActorRequest request) {
        return actorRepository.findByName(request.name()).orElseGet(
                () -> actorRepository.save(actorMapper.actorRequestToActor(request))
        );
    }

    @Transactional
    public DetailedActorResponse updateActor(Long id, ActorRequest request) {
        Actor actor = actorRepository.findById(id).orElseThrow();
        actorMapper.updateActorRequestToActor(request, actor);

        Actor saved = actorRepository.save(actor);

        return actorMapper.actorToDetailedActorResponse(saved);
    }

    @Transactional
    public void deleteActor(Long id) {
        Actor actor = actorRepository.findById(id).orElseThrow();
        actor.getFilms().forEach(film -> film.removeActor(actor));
        actorRepository.delete(actor);
    }
}
