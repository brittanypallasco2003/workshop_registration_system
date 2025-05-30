package com.app.workshop_registration_system.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.workshop_registration_system.Models.DTO.LoginRequestDTO;
import com.app.workshop_registration_system.Models.DTO.RegisterUserRequestDTO;
import com.app.workshop_registration_system.Services.AuthServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    public AuthController(AuthServiceImpl authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserRequestDTO requestDTO) {
        return new ResponseEntity<>(authServiceImpl.registerUser(requestDTO), HttpStatus.OK);
    }

    @PostMapping("/log-in")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequestDTO requestDTO) {

        return new ResponseEntity<>(authServiceImpl.loginUser(requestDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createUser")
    public ResponseEntity<?> createAdmin(@RequestBody @Valid RegisterUserRequestDTO requestDTO) {

        return new ResponseEntity<>(authServiceImpl.createAdmin(requestDTO), HttpStatus.OK);
    }



}
