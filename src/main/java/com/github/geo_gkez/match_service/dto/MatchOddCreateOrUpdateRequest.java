package com.github.geo_gkez.match_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record MatchOddCreateOrUpdateRequest(
        @NotBlank
        String specifier,
        @Positive
        Double odd
) {
}
