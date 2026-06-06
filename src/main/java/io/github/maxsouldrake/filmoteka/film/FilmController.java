package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.CreateFilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public ResponseEntity<List<FilmResponse>> getFilms() {
        List<FilmResponse> response = filmService.findAll();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<DetailedFilmResponse> create(@RequestBody @Valid CreateFilmRequest request) {
        DetailedFilmResponse response = filmService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedFilmResponse> getFilm(@PathVariable Long id) {
        DetailedFilmResponse response = filmService.findById(id);

        return ResponseEntity.ok(response);
    }
}
