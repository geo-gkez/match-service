package com.github.geo_gkez.match_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Paginated response containing matches")
public record PageMatchResponse(
        @Schema(description = "List of matches for the current page")
        List<MatchDto> matchDtos,
        @Schema(description = "Total number of match odds", example = "25")
        long totalElements,
        @Schema(description = "Total number of pages", example = "3")
        int totalPages,
        @Schema(description = "Whether this is the last page", example = "false")
        boolean isLast
) {
}
