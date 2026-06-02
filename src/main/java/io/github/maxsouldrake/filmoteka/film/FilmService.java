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

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);
    }

    @Transactional
    public DetailedFilmResponse create(CreateFilmRequest request) {

        Film film = Film.builder()
                .title(request.title())
                .releaseYear(request.releaseYear())
                .country(request.country())
                .description(request.description())
                .posterUrl(request.posterUrl())
                .genres(request.genres())
                .build();

        Film saved = filmRepository.save(film);

        return new DetailedFilmResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getReleaseYear(),
                saved.getCountry(),
                saved.getDescription(),
                saved.getPosterUrl(),
                saved.getGenres(),
                null,
                null
        );
    }

    @Transactional
    public void deleteById(Long id) {
        filmRepository.deleteById(id);
    }
}
