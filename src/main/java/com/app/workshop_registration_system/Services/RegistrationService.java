package com.app.workshop_registration_system.Services;

import com.app.workshop_registration_system.Models.DTO.Request.RegistrationRequestDTO;
import com.app.workshop_registration_system.Models.DTO.Response.RegistrationResponseDTO;

public interface RegistrationService {
    RegistrationResponseDTO createRegister(RegistrationRequestDTO requestDTO);
    void cancelRegistration(Long id);
    RegistrationResponseDTO reactivateRegistration(Long id);

}
