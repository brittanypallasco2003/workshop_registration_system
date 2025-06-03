package com.app.workshop_registration_system.Validation.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.app.workshop_registration_system.Validation.Validator.ExistPhoneNumberValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ExistPhoneNumberValidation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IsExistPhoneNumber {
    String message() default "ya se encuentra registrado!";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
