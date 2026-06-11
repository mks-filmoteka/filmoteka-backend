package io.github.maxsouldrake.filmoteka.common.exception;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Error response with validation errors")
public record ValidationErrorResponse(
        @Schema(description = "error response", implementation = ErrorResponse.class)
        ErrorResponse error,

        @ArraySchema(schema = @Schema(implementation = ValidationError.class))
        List<ValidationError> validationErrors
) {
}
