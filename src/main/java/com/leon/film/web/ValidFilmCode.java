package com.leon.film.web;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FilmCodeValidator.class})
public @interface ValidFilmCode {

    String message() default "Invalid film code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
