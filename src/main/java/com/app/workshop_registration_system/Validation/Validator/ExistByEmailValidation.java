package com.app.workshop_registration_system.Validation.Validator;

import com.app.workshop_registration_system.Services.UserService;
import com.app.workshop_registration_system.Validation.Annotation.IsExistEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistByEmailValidation implements ConstraintValidator<IsExistEmail,String>{

    private final UserService userService;

    public ExistByEmailValidation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (userService!=null) {
            return !userService.existsByEmail(value);
        }
        return true;
       
    }

}
