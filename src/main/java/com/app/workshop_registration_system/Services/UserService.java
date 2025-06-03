package com.app.workshop_registration_system.Services;

import java.util.Optional;

import com.app.workshop_registration_system.Models.DTO.UserRequestDTO;
import com.app.workshop_registration_system.Models.DTO.UserResponseDTO;

public interface UserService {

    Optional<UserResponseDTO> updateDataUser(UserRequestDTO userRequestDTO, Long id);

    boolean existByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    
} 
