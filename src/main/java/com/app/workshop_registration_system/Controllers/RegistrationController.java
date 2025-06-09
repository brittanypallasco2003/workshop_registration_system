package com.app.workshop_registration_system.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.workshop_registration_system.Models.DTO.Request.RegistrationRequestDTO;
import com.app.workshop_registration_system.Models.DTO.Response.RegistrationResponseDTO;
import com.app.workshop_registration_system.Services.RegistrationServiceImpl;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationServiceImpl registrationServiceImpl;

    public RegistrationController(RegistrationServiceImpl registrationServiceImpl) {
        this.registrationServiceImpl = registrationServiceImpl;
    }

    @PreAuthorize("hasRole('PARTICIPANT')")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllRegistrationsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(registrationServiceImpl.getAllRegistrationByUser(userId));
    }
    
    @PreAuthorize("hasRole('PARTICIPANT')")
    @PostMapping
    public ResponseEntity<?> postRegister(@RequestBody @Valid RegistrationRequestDTO requestDTO) {
        RegistrationResponseDTO responseDTO = registrationServiceImpl.createRegister(requestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();
        return ResponseEntity.created(location).body(responseDTO);
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
