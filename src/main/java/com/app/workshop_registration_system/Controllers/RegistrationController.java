package com.app.workshop_registration_system.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.workshop_registration_system.Models.DTO.Request.RegistrationRequestDTO;
import com.app.workshop_registration_system.Services.RegistrationServiceImpl;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationServiceImpl registrationServiceImpl;

    public RegistrationController(RegistrationServiceImpl registrationServiceImpl) {
        this.registrationServiceImpl = registrationServiceImpl;
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PostMapping
    public ResponseEntity<?> postRegister(@RequestBody @Valid RegistrationRequestDTO requestDTO) {

        return ResponseEntity.ok().body(registrationServiceImpl.createRegister(requestDTO));
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PatchMapping("/cancel/{id}")
    public ResponseEntity<?> cancelRegistration(@PathVariable Long id) {
        registrationServiceImpl.cancelRegistration(id);
        return ResponseEntity.ok().body(Map.of("message", "Inscripción cancelada"));
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @PatchMapping("/reactivate/{id}")
    public ResponseEntity<?> reactivateRegistration(@PathVariable Long id) {

        return ResponseEntity.ok().body(registrationServiceImpl.reactivateRegistration(id));
    }

}
