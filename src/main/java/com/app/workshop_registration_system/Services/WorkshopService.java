package com.app.workshop_registration_system.Services;

import java.util.List;
import java.util.Optional;

import com.app.workshop_registration_system.Models.WorkshopModel;
import com.app.workshop_registration_system.Models.DTO.WorkshopRequestDTO;
import com.app.workshop_registration_system.Models.DTO.WorkshopResponseDTO;

public interface WorkshopService {

    List<WorkshopResponseDTO> getAllWorkshops();
    Optional<WorkshopModel> getSpecificWorkshop();
    WorkshopResponseDTO createWorkshop(WorkshopRequestDTO workshopRequestDTO);
    WorkshopModel updateWorkshop();
    void deleteWorkshop();

}
