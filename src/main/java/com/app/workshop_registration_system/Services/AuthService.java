package com.app.workshop_registration_system.Services;

import com.app.workshop_registration_system.Models.DTO.Request.LoginRequestDTO;
import com.app.workshop_registration_system.Models.DTO.Request.RegisterUserRequestDTO;
import com.app.workshop_registration_system.Models.DTO.Response.LoginResponseDTO;
import com.app.workshop_registration_system.Models.DTO.Response.RegisterResponseDTO;

public interface AuthService {

    RegisterResponseDTO registerUser(RegisterUserRequestDTO registerUserRequestDTO);
    RegisterResponseDTO createAdmin(RegisterUserRequestDTO registerAdminDTO);
    LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);


} 
