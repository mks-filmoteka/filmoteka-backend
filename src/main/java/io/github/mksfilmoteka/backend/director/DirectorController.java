package io.github.mksfilmoteka.backend.director;

import io.github.mksfilmoteka.backend.common.exception.ErrorResponse;
import io.github.mksfilmoteka.backend.director.dto.DetailedDirectorResponse;
import io.github.mksfilmoteka.backend.director.dto.DirectorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Directors", description = "Operations related to directors")
@RestController
@RequestMapping("api/v1/directors")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @Operation(
            summary = "Get director by id",
            description = "Returns director with details including list films"
    )
    @ApiResponse(responseCode = "200", description = "Director returned",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DetailedDirectorResponse.class)
            )
    )
    @ApiResponse(responseCode = "404", description = "Director not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<DetailedDirectorResponse> getDirector(@PathVariable Long id) {
        DetailedDirectorResponse response = directorService.findById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update director",
            description = "Update director fields"
    )
    @ApiResponse(responseCode = "200", description = "Director updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DetailedDirectorResponse.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Validation error",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @ApiResponse(responseCode = "404", description = "Director not found",
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
    public ResponseEntity<DetailedDirectorResponse> updateDirector(
            @PathVariable Long id,
            @RequestBody @Valid DirectorRequest request) {
        DetailedDirectorResponse response = directorService.updateDirector(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete director",
            description = "Deletes director by id"
    )
    @ApiResponse(responseCode = "204", description = "Director deleted")
    @ApiResponse(responseCode = "404", description = "Director not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }
}
