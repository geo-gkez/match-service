package com.github.geo_gkez.match_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request object for creating or updating a match odd")
public record MatchOddCreateOrUpdateRequest(
        @Schema(description = "Odd specifier (e.g., '1', 'X', '2') - required", example = "1")
        @NotBlank
        String specifier,
        @Schema(description = "Odd value - must be positive", example = "1.85")
        @Positive
        Double odd
) {
}
