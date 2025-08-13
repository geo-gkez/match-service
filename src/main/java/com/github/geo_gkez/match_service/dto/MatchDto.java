package com.github.geo_gkez.match_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.github.geo_gkez.match_service.constant.DateFormaterConstants.DATE_FORMAT_CUSTOM;
import static com.github.geo_gkez.match_service.constant.DateFormaterConstants.TIME_FORMAT_CUSTOM;

public record MatchDto(
        Long id,
        String description,
        @JsonFormat(pattern = DATE_FORMAT_CUSTOM)
        LocalDate matchDate,
        @JsonFormat(pattern = TIME_FORMAT_CUSTOM)
        LocalTime matchTime,
        String teamA,
        String teamB,
        int sport
) {
}
