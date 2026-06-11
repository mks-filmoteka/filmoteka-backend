package io.github.maxsouldrake.filmoteka.common.exception;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Standard error response")
public record ErrorResponse(
        @Schema(description = "timestamp")
        LocalDateTime timestamp,

        @Schema(description = "http status", example = "404")
        int status,

        @Schema(description = "error message", example = "Film not found")
        String message,

        @Schema(description = "method uri", example = "/api/v1/films/10000")
        String path,

        @Schema(description = "error code", example = "FILM_NOT_FOUND")
        ErrorCode code,

        @ArraySchema(schema = @Schema(implementation = ErrorDetail.class))
        List<ErrorDetail> errorDetails
) {
}
