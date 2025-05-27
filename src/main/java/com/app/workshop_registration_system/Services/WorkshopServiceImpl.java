package com.app.workshop_registration_system.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.workshop_registration_system.Models.WorkshopModel;
import com.app.workshop_registration_system.Models.DTO.WorkshopRequestDTO;
import com.app.workshop_registration_system.Models.DTO.WorkshopResponseDTO;
import com.app.workshop_registration_system.Repositories.WorkshopRepository;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<WorkshopResponseDTO> getAllWorkshops() {
        List<WorkshopModel> workshops = (List<WorkshopModel>) workshopRepository.findAll();

        return workshops.stream()
                .map(workshop -> new WorkshopResponseDTO(workshop.getId(), workshop.getName(),
                        workshop.getDescription(), workshop.getStartDate(), workshop.getAvailablePlaces(),
                        workshop.getPlace(), workshop.isActive()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<WorkshopResponseDTO> getSpecificWorkshop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSpecificWorkshop'");
    }

    @Transactional
    @Override
    public WorkshopResponseDTO createWorkshop(WorkshopRequestDTO workshopRequestDTO) {
        WorkshopModel workshopModel = WorkshopModel.builder()
                .name(workshopRequestDTO.name())
                .description(workshopRequestDTO.description())
                .startDate(workshopRequestDTO.startDate())
                .availablePlaces(workshopRequestDTO.availablePlaces())
                .place(workshopRequestDTO.place())
                .active(workshopRequestDTO.active())
                .build();

        WorkshopModel workshopCreated = workshopRepository.save(workshopModel);

        return new WorkshopResponseDTO(
            workshopCreated.getId(),
            workshopCreated.getName(),
            workshopCreated.getDescription(),
            workshopCreated.getStartDate(),
            workshopCreated.getAvailablePlaces(),
            workshopCreated.getPlace(),
            workshopCreated.isActive()
        );
    }

    @Override
    public WorkshopModel updateWorkshop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateWorkshop'");
    }

    @Override
    public void deleteWorkshop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteWorkshop'");
    }

}
