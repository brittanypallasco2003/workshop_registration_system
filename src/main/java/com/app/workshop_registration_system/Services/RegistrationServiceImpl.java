package com.app.workshop_registration_system.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
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
import com.app.workshop_registration_system.Models.DTO.Response.RegistrationResponseListDTO;
import com.app.workshop_registration_system.Models.DTO.Response.WorkshopResponseDTO;
import com.app.workshop_registration_system.Repositories.RegistrationRepository;
import com.app.workshop_registration_system.Repositories.UserRepository;
import com.app.workshop_registration_system.Repositories.WorkshopRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final WorkshopRepository workshopRepository;

    private final UserRepository userRepository;

    private final EmailService emailServiceImpl;

    private final AuthenticatedUserService authenticatedUserService;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository, WorkshopRepository workshopRepository,
            UserRepository userRepository, EmailService emailServiceImpl,
            AuthenticatedUserService authenticatedUserService) {
        this.registrationRepository = registrationRepository;
        this.workshopRepository = workshopRepository;
        this.userRepository = userRepository;
        this.emailServiceImpl = emailServiceImpl;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Transactional
    @Override
    public RegistrationResponseDTO createRegister(RegistrationRequestDTO requestDTO) {

        // 1. verificar existencia de usuario en bd
        UserModel userDb = userRepository.findById(requestDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (!authenticatedUserService.isCurrentUser(requestDTO.userId())) {
            throw new AccessDeniedException("No tienes permitido acceder este recurso");
        }
        // 2. verificar existencia del taller
        WorkshopModel workshopDb = workshopRepository.findById(requestDTO.workshopId())
                .orElseThrow(() -> new EntityNotFoundException("Taller no encontrado"));

        // 2.1 verifica que no el usuario no este inscrito previamente
        if (registrationRepository.buscarPorUsuarioYTaller(userDb.getId(), workshopDb.getId()).isPresent()) {
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
                .registrationDate(LocalDateTime.now())
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
        return mapResponseDTO(savedRegistration, workshopUpdated, "Inscripción realizada");

    }

    @Transactional
    @Override
    public void cancelRegistration(Long id) {
        RegistrationModel registrationModel = registrationRepository.findByIdWithUserAndWorkshop(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscripción no encontrada"));

        if (!authenticatedUserService.isCurrentUser(registrationModel.getUser().getId())) {
            throw new AccessDeniedException("No tienes permitido cancelar esta inscripción");
        }
        registrationModel.setStatus(StatusEnum.CANCELED);

        WorkshopModel workshopModel = registrationModel.getWorkshop();

        workshopModel.setAvailablePlaces(workshopModel.getAvailablePlaces() + 1);

        registrationRepository.save(registrationModel);
        workshopRepository.save(workshopModel);

    }

    @Transactional
    @Override
    public RegistrationResponseDTO reactivateRegistration(Long id) {
        RegistrationModel registration = registrationRepository.findByIdWithUserAndWorkshop(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscripción no realizada"));

        if (!authenticatedUserService.isCurrentUser(registration.getUser().getId())) {
            throw new AccessDeniedException("No tienes permitido reactivar esta inscripción");
        }

        if (registration.getStatus() != StatusEnum.CANCELED) {
            throw new IllegalStateException("Solo se pueden reactivar inscripciones canceladas");
        }

        registration.setStatus(StatusEnum.CONFIRMED);
        registration.setRegistrationDate(LocalDateTime.now());
        WorkshopModel workshopModel = registration.getWorkshop();

        if (!workshopModel.isActive()) {
            throw new IllegalStateException("El taller está inactivo y no acepta inscripciones");
        }

        if (workshopModel.getAvailablePlaces() == 0) {
            workshopModel.setActive(false);
            workshopRepository.save(workshopModel);
            throw new PlacesSoldOutException("No hay cupos disponibles para este taller");
        }

        workshopModel.setAvailablePlaces(workshopModel.getAvailablePlaces() - 1);
        registrationRepository.save(registration);
        WorkshopModel worshopUpdated = workshopRepository.save(workshopModel);
        return mapResponseDTO(registration, worshopUpdated, "Inscripción actualizada");
    }

    @Override
    public List<RegistrationResponseListDTO> getAllRegistrationByUser(Long id) {
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (!authenticatedUserService.isCurrentUser(id)) {
            throw new AccessDeniedException("No tienes permitido ver estas inscripciones");
        }

        List<RegistrationModel> registrationsByUser = registrationRepository
                .getRegistrationsByUserId(userModel.getId());

        return registrationsByUser.stream()
                .map(r -> {
                    return new RegistrationResponseListDTO(r.getId(),
                            r.getUser().getId(),
                            mapWorkshopResponseDTO(r.getWorkshop()),
                            r.getRegistrationDate(),
                            r.getStatus().toString());
                })
                .collect(Collectors.toList());
    }

    private RegistrationResponseDTO mapResponseDTO(RegistrationModel registration, WorkshopModel workshopModel,
            String message) {
        return new RegistrationResponseDTO(message,
                registration.getId(),
                registration.getUser().getId(),
                mapWorkshopResponseDTO(workshopModel),
                registration.getRegistrationDate(),
                registration.getStatus().toString());
    }

    private WorkshopResponseDTO mapWorkshopResponseDTO(WorkshopModel workshopModel) {
        return new WorkshopResponseDTO(workshopModel.getId(), workshopModel.getName(), workshopModel.getDescription(),
                workshopModel.getStartDate(), workshopModel.getAvailablePlaces(), workshopModel.getPlace(),
                workshopModel.isActive());
    }

    private Map<String, String> createDataEmail(WorkshopModel workshopModel) {
        LocalDateTime fechaHora = workshopModel.getStartDate();
        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
        String fecha = fechaHora.format(formatterFecha);
        String hora = fechaHora.format(formatterHora);

        return Map.of("taller", workshopModel.getName(), "fecha", fecha, "hora", hora);
    }

}
