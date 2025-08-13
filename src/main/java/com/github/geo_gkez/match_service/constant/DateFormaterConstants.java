package com.github.geo_gkez.match_service.constant;

import static com.github.geo_gkez.match_service.constant.MatchServiceConstants.UTILITY_CLASS;

public final class DateFormaterConstants {
    public static final String DATE_FORMAT_CUSTOM = "dd/MM/yyyy";
    public static final String TIME_FORMAT_CUSTOM = "HH:mm";

    private DateFormaterConstants() throws IllegalAccessException {
        throw new IllegalAccessException(UTILITY_CLASS);
    }
}
