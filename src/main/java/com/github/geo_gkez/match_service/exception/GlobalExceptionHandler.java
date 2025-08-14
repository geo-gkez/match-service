package com.github.geo_gkez.match_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.geo_gkez.match_service.constant.MatchServiceConstants.MATCH_UNIQUE_CONSTRAIN;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed for one or more arguments");

        Map<String, String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .collect(Collectors
                        .toMap(
                                FieldError::getField,
                                fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Invalid value for field: " + fieldError.getField()
                        )
                );

        problemDetail.setProperties(Map.of("constrainViolations", errors));

        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
        log.error(dataIntegrityViolationException.getMessage(), dataIntegrityViolationException);

        String detail = "Data integrity violation";

        if (dataIntegrityViolationException.getCause() != null && dataIntegrityViolationException.getCause() instanceof ConstraintViolationException constraintViolationException) {
            String constrainName = constraintViolationException.getConstraintName();
            if (Objects.equals(constrainName, MATCH_UNIQUE_CONSTRAIN)) {
                detail = "Match with the same date, time, teams and sport already exists";
            } else {
                detail = "Constraint violation: " + constrainName;
            }
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, detail);
        problemDetail.setTitle("Data Integrity Violation");

        return problemDetail;
    }

    @ExceptionHandler(CRUDOperationsException.class)
    public ProblemDetail handleCRUDOperationsException(CRUDOperationsException crudOperationsException) {
        log.error(crudOperationsException.getMessage(), crudOperationsException);

        if (crudOperationsException.getCause() != null && crudOperationsException.getCause() instanceof DataIntegrityViolationException dataIntegrityViolationException) {
            return handleDataIntegrityViolationException(dataIntegrityViolationException);
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(crudOperationsException.getStatusCode()), crudOperationsException.getMessage());
        problemDetail.setTitle("CRUD Operation Error");

        if (crudOperationsException.getMatchId() != null) {
            problemDetail.setProperty("matchId", crudOperationsException.getMatchId());
        }

        if (crudOperationsException.getMatchOddId() != null) {
            problemDetail.setProperty("matchOddId", crudOperationsException.getMatchOddId());
        }

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid type for argument: " + exception.getName());
        problemDetail.setTitle("Type Mismatch Error");

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception) {
        log.error(exception.getMessage(), exception);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty("error", "Unexpected error ");

        return problemDetail;
    }
}
