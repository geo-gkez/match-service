package com.github.geo_gkez.match_service.exception;

import lombok.Getter;

@Getter
public class CRUDOperationsException extends RuntimeException {
    private final CRUDOpertionsEnum operation;
    private final Long matchId;
    private final Long matchOddId;
    private final int statusCode;

    public CRUDOperationsException(CRUDOpertionsEnum operation, Long matchId, int statusCode) {
        this(operation, matchId, null, statusCode, null);
    }

    public CRUDOperationsException(CRUDOpertionsEnum operation, Long matchId, int statusCode, Throwable cause) {
        this(operation, matchId, null, statusCode, cause);
    }

    public CRUDOperationsException(CRUDOpertionsEnum operation, Long matchId, Long matchOddId, int statusCode) {
        this(operation, matchId, matchOddId, statusCode, null);
    }

    public CRUDOperationsException(CRUDOpertionsEnum operation, Long matchId, Long matchOddId, int statusCode, Throwable cause) {
        super(buildMessage(operation, matchId, matchOddId), cause);
        this.operation = operation;
        this.matchId = matchId;
        this.matchOddId = matchOddId;
        this.statusCode = statusCode;
    }

    public CRUDOperationsException(CRUDOpertionsEnum operation, Throwable cause, int statusCode) {
        this(operation, null, null, statusCode, cause);
    }

    private static String buildMessage(CRUDOpertionsEnum operation, Long matchId, Long matchOddId) {
        String baseMessage = operation.getExceptionMessage();

        if (matchId != null) {
            baseMessage += " for match ID: " + matchId;

            if (matchOddId != null) {
                baseMessage += " and match odd ID: " + matchOddId;
            }
        } else if (matchOddId != null) {
            baseMessage += " for match odd ID: " + matchOddId;
        }

        return baseMessage;
    }
}
