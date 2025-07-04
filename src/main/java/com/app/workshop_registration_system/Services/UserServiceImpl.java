package com.app.workshop_registration_system.Services;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.workshop_registration_system.Advice.ForbiddenOperationException;
import com.app.workshop_registration_system.Models.UserModel;
import com.app.workshop_registration_system.Models.DTO.Request.UserRequestDTO;
import com.app.workshop_registration_system.Models.DTO.Response.UserResponseDTO;
import com.app.workshop_registration_system.Repositories.UserRepository;

import jakarta.persistence.EntityManager;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final AuthenticatedUserService authenticatedUserService;

    public UserServiceImpl(UserRepository userRepository, EntityManager entityManager, AuthenticatedUserService authenticatedUserService) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
        this.authenticatedUserService=authenticatedUserService;
    }

    @Transactional
    @Override
    public Optional<UserResponseDTO> updateDataUser(UserRequestDTO userRequestDTO, Long id) {
      return userRepository.findById(id)
      .map(user ->{
        if (!authenticatedUserService.isCurrentUser(id)) {
          throw new AccessDeniedException("No puedes actualizar los datos de este usuario");
        }
        user.setName(userRequestDTO.name()!=null? userRequestDTO.name():user.getName());

        user.setLastname(userRequestDTO.lastname()!=null? userRequestDTO.lastname():user.getLastname());

        user.setPhoneNumber(userRequestDTO.phoneNumber()!=null? userRequestDTO.phoneNumber():user.getPhoneNumber());

        UserModel userUpdated=userRepository.save(user);
        entityManager.flush();
        entityManager.refresh(userUpdated);

        return new UserResponseDTO("Datos actualizados", userUpdated.getId(), userUpdated.getName(), userUpdated.getLastname(), userUpdated.getPhoneNumber(), userUpdated.getUpdatedAt());
      });
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }


    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
