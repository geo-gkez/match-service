package com.github.geo_gkez.match_service.dto;

import java.util.List;

public record PageMatchOddResponse(
        List<MatchOddDto> matchOddsDtos,
        long totalElements,
        int totalPages,
        boolean isLast
) {
}
