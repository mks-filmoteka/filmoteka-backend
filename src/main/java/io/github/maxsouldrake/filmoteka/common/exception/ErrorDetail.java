package io.github.maxsouldrake.filmoteka.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Validation error")
public record ErrorDetail(
        @Schema(description = "invalid field", example = "title")
        String field,

        @Schema(description = "error message", example = "must not be blank")
        String message
) {
}
