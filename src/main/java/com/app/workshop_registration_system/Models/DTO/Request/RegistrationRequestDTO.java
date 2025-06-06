package com.app.workshop_registration_system.Models.DTO.Request;

import jakarta.validation.constraints.Min;

public record RegistrationRequestDTO(
@Min(value = 1)
Long userId, 
@Min(value = 1)
Long workshopId) {

}
