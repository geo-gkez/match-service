package com.github.geo_gkez.match_service.validation;

import com.github.geo_gkez.match_service.entity.enums.SportEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SportValidator implements ConstraintValidator<SportCheck, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                "Sport must have a valid code " + SportEnum.validValues()
        ).addConstraintViolation();

        return SportEnum.isValid(value);
    }
}
