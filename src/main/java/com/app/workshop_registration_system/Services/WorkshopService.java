package com.app.workshop_registration_system.Services;

import java.util.List;
import java.util.Optional;

import com.app.workshop_registration_system.Models.DTO.Request.WorkshopRequestDTO;
import com.app.workshop_registration_system.Models.DTO.Response.WorkshopResponseDTO;

public interface WorkshopService {

    List<WorkshopResponseDTO> getAllActiveWorkshops();
    List<WorkshopResponseDTO> getAllWorkshopsAdmin();

    Optional<WorkshopResponseDTO> getSpecificWorkshop(Long id);

    WorkshopResponseDTO createWorkshop(WorkshopRequestDTO workshopRequestDTO);

    Optional<WorkshopResponseDTO> updateWorkshop(WorkshopRequestDTO workshopRequestDTO, Long id);

    boolean deleteWorkshop(Long id);

}
