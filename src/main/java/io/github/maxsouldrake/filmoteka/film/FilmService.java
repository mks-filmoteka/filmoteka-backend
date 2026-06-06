package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.actor.ActorService;
import io.github.maxsouldrake.filmoteka.director.DirectorService;
import io.github.maxsouldrake.filmoteka.film.dto.CreateFilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilmService {

    private final FilmRepository filmRepository;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final FilmMapper filmMapper;

    public List<FilmResponse> findAll() {
        return filmMapper.filmsToFilmResponses(filmRepository.findAll());
    }

    public DetailedFilmResponse findById(Long id) {
        Film film = filmRepository.findById(id).orElseThrow();
        return filmMapper.filmToDetailedFilmResponse(film);
    }

    @Transactional
    public DetailedFilmResponse create(CreateFilmRequest request) {

        Film film = filmMapper.createFilmRequestToFilm(request);

        request.actors().stream()
                .map(actorService::findOrCreate)
                .forEach(film::addActor);
        request.directors().stream()
                .map(directorService::findOrCreate)
                .forEach(film::addDirector);

        Film saved = filmRepository.save(film);

        return filmMapper.filmToDetailedFilmResponse(saved);
    }

    @Transactional
    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }
}
