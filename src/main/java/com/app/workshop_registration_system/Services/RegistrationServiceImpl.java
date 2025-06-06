package com.app.workshop_registration_system.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.workshop_registration_system.Advice.EntityNotFoundException;
import com.app.workshop_registration_system.Advice.PlacesSoldOutException;
import com.app.workshop_registration_system.Advice.RegistrationPersistenceException;
import com.app.workshop_registration_system.Models.RegistrationModel;
import com.app.workshop_registration_system.Models.StatusEnum;
import com.app.workshop_registration_system.Models.UserModel;
import com.app.workshop_registration_system.Models.WorkshopModel;
import com.app.workshop_registration_system.Models.DTO.Request.RegistrationRequestDTO;
import com.app.workshop_registration_system.Models.DTO.Response.RegistrationResponseDTO;
import com.app.workshop_registration_system.Repositories.RegistrationRepository;
import com.app.workshop_registration_system.Repositories.UserRepository;
import com.app.workshop_registration_system.Repositories.WorkshopRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final WorkshopRepository workshopRepository;

    private final UserRepository userRepository;

    private final EmailServiceImpl emailServiceImpl;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository, WorkshopRepository workshopRepository,
            UserRepository userRepository, EmailServiceImpl emailServiceImpl) {
        this.registrationRepository = registrationRepository;
        this.workshopRepository = workshopRepository;
        this.userRepository = userRepository;
        this.emailServiceImpl = emailServiceImpl;
    }

    @Transactional
    @Override
    public RegistrationResponseDTO createRegister(RegistrationRequestDTO requestDTO) {

        // 1. verificar existencia de usuario en bd
        UserModel userDb = userRepository.findById(requestDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // 2. verificar existencia del taller 
        WorkshopModel workshopDb = workshopRepository.findById(requestDTO.workshopId())
                .orElseThrow(() -> new EntityNotFoundException("Taller no encontrado"));

        // 2.1 verifica que no el usuario no este inscrito previamente
        if (registrationRepository.buscarPorUsuarioYTaller(userDb.getId(), userDb.getId()).isPresent()) {
            throw new IllegalStateException("El usuario ya está inscrito en este taller");
        }

        // 2.2 verifica que el taller este activo
        if (!workshopDb.isActive()) {
            throw new IllegalStateException("El taller no se encuentra activado");
        }

        // 3. verificar existencia de cupo disponibles. Si llega a cero, desactivar el
        // taller
        if (workshopDb.getAvailablePlaces() == 0) {
            if (workshopDb.isActive()) {
                workshopDb.setActive(false);
                workshopRepository.save(workshopDb);
            }
            throw new PlacesSoldOutException("No hay cupos disponibles para este taller");
        }

        // 4. Crear nueva inscripción y enlazar con los set en memoria
        RegistrationModel registration = RegistrationModel.builder()
                .status(StatusEnum.CONFIRMED)
                .build();

        workshopDb.addRegistration(registration);
        userDb.addRegistration(registration);
        workshopDb.setAvailablePlaces(workshopDb.getAvailablePlaces() - 1);
        // Al guardar el workshop, también se guardan las inscripciones gracias a
        // CascadeType.ALL

        // 6. Insertar la inscripción en la bd
        WorkshopModel workshopUpdated = workshopRepository.save(workshopDb);

        // 7. verificar inserción de registro
        RegistrationModel savedRegistration = workshopUpdated.getRegistrations().stream()
                .filter(r -> r.getUser().getId().equals(userDb.getId()))
                .findFirst()
                .orElseThrow(() -> new RegistrationPersistenceException("La inscripción no se guardo correctamente"));

        // 8. Envió de correo electrónico

        Map<String, String> data = createDataEmail(workshopDb);
        emailServiceImpl.sendEmail(userDb.getEmail(), "Inscripción: ".concat(workshopDb.getName()), data,
                "registration.html");

        // 9. devolver DTO
        return mapResponseDTO(savedRegistration,"Inscripción realizada");

    }

    private RegistrationResponseDTO mapResponseDTO(RegistrationModel registration, String message) {
        return new RegistrationResponseDTO(message,registration.getId(), registration.getUser().getId(),
                registration.getWorkshop().getId(), registration.getRegistrationDate(),
                registration.getStatus().name());

    }

    private Map<String, String> createDataEmail(WorkshopModel workshopModel) {
        LocalDateTime fechaHora = workshopModel.getStartDate();
        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
        String fecha = fechaHora.format(formatterFecha);
        String hora = fechaHora.format(formatterHora);

        return Map.of("taller", workshopModel.getName(), "fecha", fecha, "hora", hora);
    }

    @Transactional
    @Override
    public void cancelRegistration(Long id) {
        RegistrationModel registrationModel = registrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscripción no encontrada"));

        registrationModel.setStatus(StatusEnum.CANCELED);

        WorkshopModel workshopModel = registrationModel.getWorkshop();

        workshopModel.setAvailablePlaces(workshopModel.getAvailablePlaces() + 1);

        registrationRepository.save(registrationModel);
        workshopRepository.save(workshopModel);

    }

    @Transactional
    @Override
    public RegistrationResponseDTO reactivateRegistration(Long id) {
        RegistrationModel registration=registrationRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Inscripción no realizada"));

        if (registration.getStatus()!=StatusEnum.CANCELED) {
            throw new IllegalStateException("Solo se pueden reactivar inscripciones canceladas");
        }

        registration.setStatus(StatusEnum.CONFIRMED);
        WorkshopModel workshopModel= registration.getWorkshop();

        if (!workshopModel.isActive()) {
            throw new IllegalStateException("El taller está inactivo y no acepta inscripciones");
        }

        if (workshopModel.getAvailablePlaces()==0) {
            workshopModel.setActive(false);
            workshopRepository.save(workshopModel);
            throw new PlacesSoldOutException("No hay cupos disponibles para este taller");
        }

        workshopModel.setAvailablePlaces(workshopModel.getAvailablePlaces()-1);
        registrationRepository.save(registration);
        workshopRepository.save(workshopModel);
        return mapResponseDTO(registration, "Inscripción actualizada");
    }

    

}
