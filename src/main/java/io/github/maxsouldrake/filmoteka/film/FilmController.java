package io.github.maxsouldrake.filmoteka.film;

import io.github.maxsouldrake.filmoteka.common.PageResponse;
import io.github.maxsouldrake.filmoteka.film.dto.DetailedFilmResponse;
import io.github.maxsouldrake.filmoteka.film.dto.FilmFilter;
import io.github.maxsouldrake.filmoteka.film.dto.FilmRequest;
import io.github.maxsouldrake.filmoteka.film.dto.FilmResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Films", description = "Operations related to films")
@RestController
@RequestMapping("api/v1/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @Operation(
            summary = "Get list of films",
            description = "Returns page of films, filtered and sorted"
    )
    @ApiResponse(responseCode = "200", description = "Page returned")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @GetMapping
    public ResponseEntity<PageResponse<FilmResponse>> getFilms(
            @ParameterObject @Valid FilmFilter filter,
            @ParameterObject Pageable pageable) {
        int pageNumber = Math.max(pageable.getPageNumber(), 0);
        int pageSize = 100;
        Sort sort = pageable.getSort().isSorted()
                ? pageable.getSort().and(Sort.by("id").ascending())
                : Sort.by("id").ascending();

        Pageable fixedPageable = PageRequest.of(pageNumber, pageSize, sort);

        PageResponse<FilmResponse> response = filmService.getFilms(filter, fixedPageable);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get film by id",
            description = "Returns film with details including list of actors and directors"
    )
    @ApiResponse(responseCode = "200", description = "Film returned")
    @ApiResponse(responseCode = "404", description = "Film not found")
    @GetMapping("/{id}")
    public ResponseEntity<DetailedFilmResponse> getFilm(@PathVariable Long id) {
        DetailedFilmResponse response = filmService.findById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create new film",
            description = "Creates a film with actors, directors and genres"
    )
    @ApiResponse(responseCode = "201", description = "Film created")
    @ApiResponse(responseCode = "400", description = "Validation error")
    @ApiResponse(responseCode = "409", description = "Film already exists")
    @PostMapping
    public ResponseEntity<DetailedFilmResponse> createFilm(@RequestBody @Valid FilmRequest request) {
        DetailedFilmResponse response = filmService.createFilm(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Update film",
            description = "Update film fields"
    )
    @ApiResponse(responseCode = "200", description = "Film updated")
    @ApiResponse(responseCode = "400", description = "Validation error")
    @ApiResponse(responseCode = "404", description = "Film not found")
    @ApiResponse(responseCode = "409", description = "Title and releaseYear conflict")
    @PutMapping("/{id}")
    public ResponseEntity<DetailedFilmResponse> updateFilm(
            @PathVariable Long id,
            @RequestBody @Valid FilmRequest request) {
        DetailedFilmResponse response = filmService.updateFilm(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete film",
            description = "Deletes film by id"
    )
    @ApiResponse(responseCode = "204", description = "Film deleted")
    @ApiResponse(responseCode = "404", description = "Film not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }
}
