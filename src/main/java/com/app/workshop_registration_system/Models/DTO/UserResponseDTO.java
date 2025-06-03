package com.app.workshop_registration_system.Models.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "message", "id", "name", "lastname", "phoneNumber", "updateAt" })
public record UserResponseDTO(String message, Long id, String name, String lastname, String phoneNumber, LocalDateTime updateAt) {

}
