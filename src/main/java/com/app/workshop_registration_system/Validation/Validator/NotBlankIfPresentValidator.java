package com.app.workshop_registration_system.Validation.Validator;

import com.app.workshop_registration_system.Validation.Annotation.NotBlankIfPresent;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankIfPresentValidator implements ConstraintValidator<NotBlankIfPresent, String> {

    /*
     * true si el valor es null → o sea, no se envió el campo (válido en un PATCH).
     * 
     * true si el valor no está en blanco → es decir, tiene texto útil.
     * 
     * false si el valor es una cadena en blanco (" "), lo cual no se permite.
     * 
     */
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || !value.isBlank();
    }

}
