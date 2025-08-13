package com.github.geo_gkez.match_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.github.geo_gkez.match_service.constant.DateFormaterConstants.DATE_FORMAT_CUSTOM;
import static com.github.geo_gkez.match_service.constant.DateFormaterConstants.TIME_FORMAT_CUSTOM;

@Schema(description = "Match information")
public record MatchDto(
        @Schema(description = "Unique identifier of the match", example = "1")
        Long id,
        @Schema(description = "Description of the match", example = "Real Madrid - Barcelona")
        String description,
        @Schema(description = "Date of the match", example = "02/08/2024", pattern = DATE_FORMAT_CUSTOM)
        @JsonFormat(pattern = DATE_FORMAT_CUSTOM)
        LocalDate matchDate,
        @Schema(description = "Time of the match", example = "20:30", pattern = TIME_FORMAT_CUSTOM)
        @JsonFormat(pattern = TIME_FORMAT_CUSTOM)
        LocalTime matchTime,
        @Schema(description = "First team", example = "Real Madrid")
        String teamA,
        @Schema(description = "Second team", example = "Barcelona")
        String teamB,
        @Schema(description = "Sport type identifier", example = "1")
        Integer sport
) {
}
