package com.leon.film.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FilmCodeValidator implements ConstraintValidator<ValidFilmCode, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        if (!value.toLowerCase().contains("arm")) {
            result = false;
        }

        return result;
    }

    @Override
    public void initialize(ValidFilmCode constraintAnnotation) {

    }

}
