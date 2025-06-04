package com.app.workshop_registration_system.Models.DTO.Response;

import java.time.LocalDateTime;

public record WorkshopResponseDTO(
    Long id,
    String name,
    String description,
    LocalDateTime startDate,
    Integer availablePlaces,
    String place, 
    boolean active) {}
