package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.actor.ActorService;
import io.github.maxsouldrake.filmoteka.common.PageResponse;
import io.github.maxsouldrake.filmoteka.director.DirectorService;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public PageResponse<FilmResponse> getFilms(String title, Pageable pageable) {
        Page<Film> page = title == null || title.isBlank()
                ? filmRepository.findAll(pageable)
                : filmRepository.findByTitleContainingIgnoreCase(title, pageable);
        List<FilmResponse> content = filmMapper.filmsToFilmResponses(page.getContent());

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public DetailedFilmResponse findById(Long id) {
        Film film = filmRepository.findById(id).orElseThrow();
        return filmMapper.filmToDetailedFilmResponse(film);
    }

    @Transactional
    public DetailedFilmResponse createFilm(FilmRequest request) {

        Film film = filmMapper.filmRequestToFilm(request);

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
    public DetailedFilmResponse updateFilm(Long id, FilmRequest request) {
        Film film = filmRepository.findById(id).orElseThrow();
        filmMapper.updateFilmRequestToFilm(request, film);

        film.getActors().clear();
        request.actors().stream()
                .map(actorService::findOrCreate)
                .forEach(film::addActor);

        film.getDirectors().clear();
        request.directors().stream()
                .map(directorService::findOrCreate)
                .forEach(film::addDirector);

        Film saved = filmRepository.save(film);

        return filmMapper.filmToDetailedFilmResponse(saved);
    }

    @Transactional
    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }
}
