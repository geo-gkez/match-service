package com.github.geo_gkez.match_service.entity.enums;

import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum SportEnum {
    FOOTBALL(1, "Football"),
    BASKETBALL(2, "Basketball");

    private final Integer code;
    private final String displayName;

    SportEnum(Integer code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static SportEnum fromValue(Integer code) {
        if (code == null) {
            return null;
        }

        return Stream.of(SportEnum.values())
                .filter(s -> Objects.equals(s.getCode(), code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown sport code: " + code));
    }

    public static boolean isValid(Integer code) {
        return Stream.of(SportEnum.values())
                .anyMatch(s -> Objects.equals(s.getCode(), code));
    }

    public static Map<Integer, String> validValues() {
        return Stream.of(SportEnum.values())
                .collect(Collectors
                        .toMap(
                                SportEnum::getCode,
                                SportEnum::getDisplayName
                        ));
    }
}
