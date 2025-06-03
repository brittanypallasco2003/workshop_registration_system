package com.app.workshop_registration_system.Validation.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.app.workshop_registration_system.Validation.Validator.NotBlankIfPresentValidator;
import com.auth0.jwt.interfaces.Payload;

import jakarta.validation.Constraint;

@Constraint(validatedBy = NotBlankIfPresentValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfPresent {
    String message() default "El campo no puede estar en blanco si está presente";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
