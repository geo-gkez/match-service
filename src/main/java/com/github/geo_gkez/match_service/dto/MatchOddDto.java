package com.github.geo_gkez.match_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Match odd information")
public record MatchOddDto(
        @Schema(description = "Unique identifier of the match odd", example = "1")
        Long id,
        @Schema(description = "Unique identifier of the related match", example = "1")
        Long matchId,
        @Schema(description = "Odd specifier (e.g., '1', 'X', '2')", example = "1")
        String specifier,
        @Schema(description = "Odd value", example = "1.85")
        Double odd
) {
}
