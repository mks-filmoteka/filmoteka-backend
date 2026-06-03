package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.CreateFilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilmService {

    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);
    }

    @Transactional
    public DetailedFilmResponse create(CreateFilmRequest request) {

        Film film = filmMapper.createFilmRequestToFilm(request);

        Film saved = filmRepository.save(film);

        return filmMapper.filmToDetailedFilmResponse(saved);
    }

    @Transactional
    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }
}
