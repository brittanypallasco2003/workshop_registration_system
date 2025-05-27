package com.app.workshop_registration_system.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.workshop_registration_system.Models.DTO.WorkshopRequestDTO;
import com.app.workshop_registration_system.Services.WorkshopServiceImpl;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/workshop")
public class WorkshopController {

    private final WorkshopServiceImpl workshopServiceImpl;

    public WorkshopController(WorkshopServiceImpl workshopServiceImpl) {
        this.workshopServiceImpl = workshopServiceImpl;
    }

    @GetMapping
    public ResponseEntity<?> getAllWorkshops() {
        return ResponseEntity.ok().body(workshopServiceImpl.getAllWorkshops());
    }

    @PostMapping
    public ResponseEntity<?> postMethodName(@RequestBody @Valid WorkshopRequestDTO workshopRequestDTO) {        
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(workshopServiceImpl.createWorkshop(workshopRequestDTO));
    }
    
    

}
