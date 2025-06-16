package com.app.workshop_registration_system.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.workshop_registration_system.Advice.EntityNotFoundException;
import com.app.workshop_registration_system.Models.DTO.Request.UserRequestDTO;
import com.app.workshop_registration_system.Services.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PreAuthorize("hasRole('PARTICIPANT') or hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUser(@RequestBody @Valid UserRequestDTO requestDTO, @PathVariable Long id) {
        return userServiceImpl.updateDataUser(requestDTO, id).map(response->ResponseEntity.ok().body(response)).orElseThrow(()-> new EntityNotFoundException("Usuario "+id+" no registrado"));
    }

}
