package com.app.workshop_registration_system.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.workshop_registration_system.Models.WorkshopModel;
import com.app.workshop_registration_system.Models.DTO.Request.WorkshopRequestDTO;
import com.app.workshop_registration_system.Models.DTO.Response.WorkshopResponseDTO;
import com.app.workshop_registration_system.Repositories.WorkshopRepository;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<WorkshopResponseDTO> getAllActiveWorkshops() {
        List<WorkshopModel> workshops = workshopRepository.findByActiveTrueAndAvailablePlacesGreaterThanEqual(1);

        return workshops.stream()
                .map(workshop -> new WorkshopResponseDTO(workshop.getId(), workshop.getName(),
                        workshop.getDescription(), workshop.getStartDate(), workshop.getAvailablePlaces(),
                        workshop.getPlace(), workshop.isActive()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<WorkshopResponseDTO> getSpecificWorkshop(Long id) {
        Optional<WorkshopModel> workshopOptional = workshopRepository.findById(id);

        return workshopOptional.map(workshop -> {
            return new WorkshopResponseDTO(workshop.getId(), workshop.getName(), workshop.getDescription(),
                    workshop.getStartDate(), workshop.getAvailablePlaces(), workshop.getPlace(), workshop.isActive());
        });

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
                workshopCreated.isActive());
    }

    @Transactional
    @Override
    public Optional<WorkshopResponseDTO> updateWorkshop(WorkshopRequestDTO workshopRequestDTO, Long id) {
        return workshopRepository.findById(id)
                .map(workshop -> {
                    workshop.setName(workshopRequestDTO.name());
                    workshop.setDescription(workshopRequestDTO.description());
                    workshop.setStartDate(workshopRequestDTO.startDate());
                    workshop.setAvailablePlaces(workshopRequestDTO.availablePlaces());
                    workshop.setPlace(workshopRequestDTO.place());
                    workshop.setActive(workshopRequestDTO.active());

                    WorkshopModel workshopDb = workshopRepository.save(workshop);

                    return new WorkshopResponseDTO(
                            workshopDb.getId(),
                            workshopDb.getName(),
                            workshopDb.getDescription(),
                            workshopDb.getStartDate(),
                            workshopDb.getAvailablePlaces(),
                            workshopDb.getPlace(),
                            workshopDb.isActive());
                });
    }

    @Transactional
    @Override
    public boolean deleteWorkshop(Long id) {
        Optional<WorkshopModel> workshOptional = workshopRepository.findById(id);
        workshOptional.ifPresent(workshop -> workshopRepository.delete(workshop));

        return workshOptional.isPresent();
    }

    @Transactional(readOnly = true)
    @Override
    public List<WorkshopResponseDTO> getAllWorkshopsAdmin() {
        
        List<WorkshopModel> workshops=(List<WorkshopModel>) workshopRepository.findAll();

        return workshops.stream()
        .map(workshop->{
            return new WorkshopResponseDTO(
                workshop.getId(),
                workshop.getName(),
                workshop.getDescription(),
                workshop.getStartDate(),
                workshop.getAvailablePlaces(),
                workshop.getPlace(),
                workshop.isActive()
            );
        }).collect(Collectors.toList());
       
    }

}
