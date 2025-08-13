package com.github.geo_gkez.match_service.exception;

import lombok.Getter;

@Getter
public enum CRUDOpertionsEnum {
    CREATE("Create", "Create operation failed"),
    READ("Read", "Read operation failed"),
    UPDATE("Update", "Update operation failed"),
    DELETE("Delete", "Delete operation failed");

    private final String operation;
    private final String exceptionMessage;

    CRUDOpertionsEnum(String operation, String exceptionMessage) {
        this.operation = operation;
        this.exceptionMessage = exceptionMessage;
    }
}
