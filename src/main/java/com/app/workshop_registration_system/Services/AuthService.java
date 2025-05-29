package com.app.workshop_registration_system.Services;

import com.app.workshop_registration_system.Models.DTO.LoginRequestDTO;
import com.app.workshop_registration_system.Models.DTO.LoginResponseDTO;
import com.app.workshop_registration_system.Models.DTO.RegisterResponseDTO;
import com.app.workshop_registration_system.Models.DTO.RegisterUserRequestDTO;

public interface AuthService {

    RegisterResponseDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO);
    RegisterResponseDTO createAdmin(RegisterUserRequestDTO registerAdminDTO);
    LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);


} 
