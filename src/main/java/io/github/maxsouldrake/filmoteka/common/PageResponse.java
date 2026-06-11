package io.github.maxsouldrake.filmoteka.common;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Paginated response")
public record PageResponse<T>(
        @Schema(description = "Current page content")
        List<T> content,

        @Schema(description = "Current page number", example = "0")
        int page,

        @Schema(description = "Page size", example = "20")
        int size,

        @Schema(description = "Total number of elements", example = "125")
        long totalElements,

        @Schema(description = "Total number of pages", example = "7")
        int totalPages
) {
}