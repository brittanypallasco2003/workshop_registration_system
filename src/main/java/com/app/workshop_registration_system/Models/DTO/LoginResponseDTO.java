package com.app.workshop_registration_system.Models.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"email", "message","jwt"})
public record LoginResponseDTO(
        String email,
        String message,
        String jwt) {
}
