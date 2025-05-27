package com.app.workshop_registration_system.Models.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

public record WorkshopRequestDTO(
        @NotBlank String name,
        @NotBlank String description,
        @Past
        LocalDateTime startDate,
        @Min(value = 10) Integer availablePlaces,
        @NotBlank String place,
        boolean active) {
}
