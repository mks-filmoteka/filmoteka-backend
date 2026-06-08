package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.film.dto.FilmRequest;
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
    public ResponseEntity<List<FilmResponse>> getFilms(@RequestParam(required = false) String title) {
        List<FilmResponse> response = filmService.getFilms(title);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedFilmResponse> getFilm(@PathVariable Long id) {
        DetailedFilmResponse response = filmService.findById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<DetailedFilmResponse> createFilm(@RequestBody @Valid FilmRequest request) {
        DetailedFilmResponse response = filmService.createFilm(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailedFilmResponse> updateFilm(
            @PathVariable Long id,
            @RequestBody @Valid FilmRequest request) {
        DetailedFilmResponse response = filmService.updateFilm(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }
}
