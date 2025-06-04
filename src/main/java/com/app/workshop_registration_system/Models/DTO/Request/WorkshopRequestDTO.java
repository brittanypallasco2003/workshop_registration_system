package com.app.workshop_registration_system.Models.DTO.Request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record WorkshopRequestDTO(
        @NotBlank
        @Size(min = 10, max = 70)
        String name,
        @NotBlank 
        @Size(min = 10, max = 150)
        String description,
        @Future
        LocalDateTime startDate,
        @Min(value = 10)
        @Max(value = 50)
        Integer availablePlaces,
        @NotBlank String place,
        boolean active) {
}
