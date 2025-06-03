package com.app.workshop_registration_system.Validation.Validator;

import com.app.workshop_registration_system.Services.UserService;
import com.app.workshop_registration_system.Validation.Annotation.IsExistPhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistPhoneNumberValidation implements ConstraintValidator<IsExistPhoneNumber, String> {

    private final UserService userService;
    public ExistPhoneNumberValidation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       if (userService!=null) {
        return !userService.existByPhoneNumber(value);
       }
       return true;
    }

}
