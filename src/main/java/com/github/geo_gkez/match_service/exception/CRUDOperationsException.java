package com.github.geo_gkez.match_service.exception;

import lombok.Getter;

@Getter
public class CRUDOperationsException extends RuntimeException {
    private final CRUDOpertionsEnum operation;
    private final Long matchId;
    private final Long matchOddId;
    private final int statusCode;

    public CRUDOperationsException(CRUDOpertionsEnum operation, Long matchId, int statusCode) {
        super(operation.getExceptionMessage() + " for match ID: " + matchId);
        this.operation = operation;
        this.matchId = matchId;
        this.matchOddId = null;
        this.statusCode = statusCode;
    }

    public CRUDOperationsException(CRUDOpertionsEnum operation, Long matchId, Long matchOddId, int statusCode) {
        super(operation.getExceptionMessage() + " for match ID: " + matchId);
        this.operation = operation;
        this.matchId = matchId;
        this.matchOddId = matchOddId;
        this.statusCode = statusCode;
    }

    public CRUDOperationsException(CRUDOpertionsEnum operation, Throwable cause, int statusCode) {
        super(operation.getExceptionMessage(), cause);
        this.operation = operation;
        this.statusCode = statusCode;
        this.matchId = null;
        this.matchOddId = null;
    }
}
