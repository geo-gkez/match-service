package com.github.geo_gkez.match_service.constant;

import static com.github.geo_gkez.match_service.constant.MatchServiceConstants.UTILITY_CLASS;

public final class UrlPathConstants {
    public static final String API = "/api";
    public static final String V1_MATCHES = "/v1/matches";

    public static final String ODDS = "/odds";

    private UrlPathConstants() throws IllegalAccessException {
        throw new IllegalAccessException(UTILITY_CLASS);
    }
}
