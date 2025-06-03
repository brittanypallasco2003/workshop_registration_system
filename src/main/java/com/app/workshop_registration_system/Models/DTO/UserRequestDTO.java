package com.app.workshop_registration_system.Models.DTO;

import com.app.workshop_registration_system.Validation.Annotation.IsExistPhoneNumber;
import com.app.workshop_registration_system.Validation.Annotation.NotBlankIfPresent;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
                @NotBlankIfPresent @Size(min = 3, max = 20) String name,
                @NotBlankIfPresent @Size(min = 3, max = 20) String lastname,
                @Pattern(regexp = "^\\+593\\d{9}$", message = "Número de teléfono inválido") @NotBlankIfPresent @IsExistPhoneNumber String phoneNumber) {

}
