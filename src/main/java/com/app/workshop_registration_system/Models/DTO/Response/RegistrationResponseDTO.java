package com.app.workshop_registration_system.Models.DTO.Response;

import java.time.LocalDateTime;

public record RegistrationResponseDTO(String message, Long id, Long idUser, Long idWorkshop,
                LocalDateTime registrationDate,
                String status) {

}
