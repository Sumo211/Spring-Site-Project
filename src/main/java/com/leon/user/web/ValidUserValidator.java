package com.leon.user.web;

import com.leon.user.UserService;
import com.leon.user.web.dto.UserCreatedDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidUserValidator implements ConstraintValidator<ValidUser, UserCreatedDto> {

    private final UserService userService;

    public ValidUserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(UserCreatedDto userCreatedDto, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        if (userService.findUserByEmail(userCreatedDto.getEmail()).isPresent()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("There is already a user with the given email address.")
                    .addPropertyNode("email").addConstraintViolation();
            result = false;
        }

        return result;
    }

    @Override
    public void initialize(ValidUser constraintAnnotation) {

    }

}
