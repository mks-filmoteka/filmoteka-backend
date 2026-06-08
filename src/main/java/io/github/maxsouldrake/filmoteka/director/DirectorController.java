package io.github.maxsouldrake.filmoteka.director;

import io.github.maxsouldrake.filmoteka.director.dto.DetailedDirectorResponse;
import io.github.maxsouldrake.filmoteka.director.dto.DirectorRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/directors")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping("/{id}")
    public ResponseEntity<DetailedDirectorResponse> getDirector(@PathVariable Long id) {
        DetailedDirectorResponse response = directorService.findById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailedDirectorResponse> updateDirector(
            @PathVariable Long id,
            @RequestBody @Valid DirectorRequest request) {
        DetailedDirectorResponse response = directorService.updateDirector(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }
}
