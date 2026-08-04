package io.github.mksfilmoteka.catalog.actor;

import io.github.mksfilmoteka.catalog.actor.dto.ActorRequest;
import io.github.mksfilmoteka.catalog.actor.dto.DetailedActorResponse;
import io.github.mksfilmoteka.catalog.common.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Actors", description = "Operations related to actors")
@RestController
@RequestMapping("/api/v1/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @Operation(
            summary = "Get actor by id",
            description = "Returns actor with details including list films"
    )
    @ApiResponse(responseCode = "200", description = "Actor returned",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DetailedActorResponse.class)
            )
    )
    @ApiResponse(responseCode = "404", description = "Actor not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<DetailedActorResponse> getActor(@PathVariable Long id) {
        DetailedActorResponse response = actorService.findById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update actor",
            description = "Update actor fields"
    )
    @ApiResponse(responseCode = "200", description = "Actor updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DetailedActorResponse.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Validation error",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @ApiResponse(responseCode = "404", description = "Actor not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @ApiResponse(responseCode = "409", description = "Name conflict",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<DetailedActorResponse> updateActor(
            @PathVariable Long id,
            @RequestBody @Valid ActorRequest request) {
        DetailedActorResponse response = actorService.updateActor(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete actor",
            description = "Deletes actor by id"
    )
    @ApiResponse(responseCode = "204", description = "Actor deleted")
    @ApiResponse(responseCode = "404", description = "Actor not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }
}
