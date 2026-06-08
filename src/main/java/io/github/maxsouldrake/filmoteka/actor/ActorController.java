package io.github.maxsouldrake.filmoteka.actor;

import io.github.maxsouldrake.filmoteka.actor.dto.ActorRequest;
import io.github.maxsouldrake.filmoteka.actor.dto.DetailedActorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @GetMapping("/{id}")
    public ResponseEntity<DetailedActorResponse> getActor(@PathVariable Long id) {
        DetailedActorResponse response = actorService.findById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailedActorResponse> updateActor(
            @PathVariable Long id,
            @RequestBody @Valid ActorRequest request) {
        DetailedActorResponse response = actorService.updateActor(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }
}
