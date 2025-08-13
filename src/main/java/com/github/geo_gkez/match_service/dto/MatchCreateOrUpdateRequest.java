package com.github.geo_gkez.match_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.geo_gkez.match_service.validation.SportCheck;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.github.geo_gkez.match_service.constant.DateFormaterConstants.DATE_FORMAT_CUSTOM;
import static com.github.geo_gkez.match_service.constant.DateFormaterConstants.TIME_FORMAT_CUSTOM;

@Schema(description = "Request object for creating or updating a match")
public record MatchCreateOrUpdateRequest(
        @Schema(description = "Description of the match (optional, will be auto-generated if not provided)", 
                example = "Real Madrid - Barcelona")
        String description,
        @Schema(description = "Date of the match", example = "02/08/2024", pattern = DATE_FORMAT_CUSTOM)
        @JsonFormat(pattern = DATE_FORMAT_CUSTOM)
        LocalDate matchDate,
        @Schema(description = "Time of the match", example = "20:30", pattern = TIME_FORMAT_CUSTOM)
        @JsonFormat(pattern = TIME_FORMAT_CUSTOM)
        LocalTime matchTime,
        @Schema(description = "First team (required)", example = "Real Madrid")
        @NotBlank
        String teamA,
        @Schema(description = "Second team (required)", example = "Barcelona")
        @NotBlank
        String teamB,
        @Schema(description = "Sport type identifier", example = "1")
        @SportCheck
        Integer sport
) {
    public MatchCreateOrUpdateRequest(String description,
                                      LocalDate matchDate,
                                      LocalTime matchTime,
                                      String teamA,
                                      String teamB,
                                      Integer sport) {
        this.description = description == null ? defaultDescription(teamA, teamB) : description;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.teamA = teamA;
        this.teamB = teamB;
        this.sport = sport;
    }

    public MatchCreateOrUpdateRequest(LocalDate matchDate, LocalTime matchTime, String teamA, String teamB, Integer sport) {
        this(defaultDescription(teamA, teamB), matchDate, matchTime, teamA, teamB, sport);
    }

    private static String defaultDescription(String teamA, String teamB) {
        if (teamA == null || teamB == null || teamA.isBlank() || teamB.isBlank()) {
            return null;
        }

        return teamA + " - " + teamB;
    }
}
