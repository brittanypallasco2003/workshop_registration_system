package com.app.workshop_registration_system.Services;

import java.util.List;

import com.app.workshop_registration_system.Models.DTO.Request.RegistrationRequestDTO;
import com.app.workshop_registration_system.Models.DTO.Response.RegistrationResponseDTO;
import com.app.workshop_registration_system.Models.DTO.Response.RegistrationResponseListDTO;

public interface RegistrationService {
    RegistrationResponseDTO createRegister(RegistrationRequestDTO requestDTO);
    void cancelRegistration(Long id);
    RegistrationResponseDTO reactivateRegistration(Long id);
    List<RegistrationResponseListDTO> getAllRegistrationByUser(Long id);

}
