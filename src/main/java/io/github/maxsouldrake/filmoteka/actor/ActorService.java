package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    public Optional<Actor> findById(Long id) {
        return actorRepository.findById(id);
    }

    @Transactional
    public Actor findOrCreate(ActorRequest request) {
        return actorRepository.findByName(request.name()).orElseGet(
                () -> actorRepository.save(actorMapper.actorRequestToActor(request))
        );
    }

    @Transactional
    public void deleteById(Long id) {
        actorRepository.deleteById(id);
    }
}
