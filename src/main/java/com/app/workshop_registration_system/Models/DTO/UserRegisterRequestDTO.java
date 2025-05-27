package com.app.workshop_registration_system.Models.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDTO(
        @NotBlank
        @Size(min = 3, max = 20) 
        String name,
        @Size(min = 3, max = 20) 
        @NotBlank String lastname,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "^([0-9]{2,3})([0-9]{7})$", message = "El número de teléfono ingresado no es válido") String phoneNumber) {
}
