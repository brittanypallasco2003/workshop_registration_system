package com.app.workshop_registration_system.Models.DTO.Response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "idUser", "workshopResponseDTO", "registrationDate", "status" })
public record RegistrationResponseListDTO(
        Long id, Long idUser, WorkshopResponseDTO workshopResponseDTO,
        LocalDateTime registrationDate,
        String status) {

}
