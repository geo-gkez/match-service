package com.github.geo_gkez.match_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = SportValidator.class)
public @interface SportCheck {
    String message() default "must be any of enum {0}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
