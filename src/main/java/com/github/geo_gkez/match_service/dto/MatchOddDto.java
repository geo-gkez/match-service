package com.github.geo_gkez.match_service.dto;

public record MatchOddDto(
        Long id,
        Long matchId,
        String specifier,
        Double odd
) {
}
