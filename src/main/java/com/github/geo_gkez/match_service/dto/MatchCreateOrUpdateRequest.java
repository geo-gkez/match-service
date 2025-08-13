package com.github.geo_gkez.match_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.geo_gkez.match_service.validation.SportCheck;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.github.geo_gkez.match_service.constant.DateFormaterConstants.DATE_FORMAT_CUSTOM;
import static com.github.geo_gkez.match_service.constant.DateFormaterConstants.TIME_FORMAT_CUSTOM;


public record MatchCreateOrUpdateRequest(
        String description,
        @JsonFormat(pattern = DATE_FORMAT_CUSTOM)
        LocalDate matchDate,
        @JsonFormat(pattern = TIME_FORMAT_CUSTOM)
        LocalTime matchTime,
        @NotBlank
        String teamA,
        @NotBlank
        String teamB,
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
