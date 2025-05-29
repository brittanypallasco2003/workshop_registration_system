package com.app.workshop_registration_system.Models.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "message", "id", "name", "lastname", "email", "phoneNumber", "createdAt" })
public record RegisterResponseDTO(String message, Long id, String name, String lastname, String email,
                String phoneNumber,
                LocalDateTime createdAt) {
}
