package io.github.mksfilmoteka.backend.director;

import io.github.mksfilmoteka.backend.common.exception.ConflictException;
import io.github.mksfilmoteka.backend.common.exception.ResourceNotFoundException;
import io.github.mksfilmoteka.backend.director.dto.DetailedDirectorResponse;
import io.github.mksfilmoteka.backend.director.dto.DirectorRequest;
import io.github.mksfilmoteka.backend.film.Film;
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
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;

    public DetailedDirectorResponse findById(Long id) {
        Director director = getDirectorOrThrow(id);
        return directorMapper.directorToDetailedDirectorResponse(director);
    }

    @Transactional
    public Director findOrCreate(DirectorRequest request) {
        Optional<Director> director = directorRepository.findByName(request.name());
        if (director.isPresent()) {
            return director.get();
        }
        Director saved = directorRepository.save(directorMapper.directorRequestToDirector(request));
        log.info("Created director id={}, name={}", saved.getId(), saved.getName());

        return saved;
    }

    @Transactional
    public DetailedDirectorResponse updateDirector(Long id, DirectorRequest request) {
        Director director = getDirectorOrThrow(id);
        if (!director.getName().equals(request.name()) && directorRepository.existsByName(request.name())) {
            throw new ConflictException("Director with name " + request.name() + " already exists");
        }
        directorMapper.updateDirectorRequestToDirector(request, director);

        Director saved = directorRepository.save(director);
        log.info("Updated director id={} with name={}", saved.getId(), saved.getName());

        return directorMapper.directorToDetailedDirectorResponse(saved);
    }

    @Transactional
    public void deleteDirector(Long id) {
        Director director = getDirectorOrThrow(id);
        for (Film film : new ArrayList<>(director.getFilms())) {
            film.removeDirector(director);
        }
        directorRepository.delete(director);
        log.info("Deleted director id={}", id);
    }

    private Director getDirectorOrThrow(Long id) {
        return directorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Director with id " + id + " not found"));
    }
}
