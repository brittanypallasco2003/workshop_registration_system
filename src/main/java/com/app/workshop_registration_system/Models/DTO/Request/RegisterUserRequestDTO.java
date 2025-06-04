package com.app.workshop_registration_system.Models.DTO.Request;

import com.app.workshop_registration_system.Validation.Annotation.IsExistEmail;
import com.app.workshop_registration_system.Validation.Annotation.IsExistPhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequestDTO(
        @NotBlank @Size(min = 3, max = 20) String name,
        @NotBlank @Size(min = 3, max = 20) String lastname,
        @NotBlank @Email @IsExistEmail String email,
        @NotBlank @Size(min = 8) String password,
        @NotBlank @Pattern(regexp = "^\\+593\\d{9}$", message = "Número de teléfono inválido") @IsExistPhoneNumber String phoneNumber) {
}
