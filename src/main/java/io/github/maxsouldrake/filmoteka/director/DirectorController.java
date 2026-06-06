package io.github.maxsouldrake.filmoteka.director;

import io.github.maxsouldrake.filmoteka.director.dto.DetailedDirectorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
