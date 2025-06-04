package com.app.workshop_registration_system.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.workshop_registration_system.Advice.EntityNotFoundException;
import com.app.workshop_registration_system.Models.DTO.Request.WorkshopRequestDTO;
import com.app.workshop_registration_system.Services.WorkshopServiceImpl;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/workshop")
public class WorkshopController {

    private final WorkshopServiceImpl workshopServiceImpl;

    public WorkshopController(WorkshopServiceImpl workshopServiceImpl) {
        this.workshopServiceImpl = workshopServiceImpl;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllWorkshopsAdmin() {
        return ResponseEntity.ok().body(workshopServiceImpl.getAllWorkshopsAdmin());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PARTICIPANT')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getSpecificWorkshop(@PathVariable Long id) {
        return workshopServiceImpl.getSpecificWorkshop(id).map(workshop -> ResponseEntity.ok().body(workshop))
                .orElseThrow(() -> new EntityNotFoundException("Taller " + id + " no encontrado"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PARTICIPANT')")
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableWorkshops() {
        return ResponseEntity.status(HttpStatus.OK).body(workshopServiceImpl.getAllActiveWorkshops());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> postWorkshop(@RequestBody @Valid WorkshopRequestDTO workshopRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workshopServiceImpl.createWorkshop(workshopRequestDTO));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> putWorkshop(@PathVariable Long id,
            @Valid @RequestBody WorkshopRequestDTO workshopRequestDTO) {
        return workshopServiceImpl.updateWorkshop(workshopRequestDTO, id)
                .map(workshopResponseDTO -> ResponseEntity.status(HttpStatus.OK).body(workshopRequestDTO))
                .orElseThrow(() -> new EntityNotFoundException("Taller " + id + " no encontrado"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkshop(@PathVariable Long id) {
        boolean deleted = workshopServiceImpl.deleteWorkshop(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Taller " + id + " no encontrado");
    }

}
