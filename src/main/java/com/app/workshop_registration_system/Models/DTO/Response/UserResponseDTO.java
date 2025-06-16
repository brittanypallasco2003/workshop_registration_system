package com.app.workshop_registration_system.Models.DTO.Response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "message", "id", "name", "lastname", "phoneNumber", "updatedAt" })
public record UserResponseDTO(String message, Long id, String name, String lastname, String phoneNumber, LocalDateTime updatedAt) {

}
