package com.app.workshop_registration_system.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.workshop_registration_system.Advice.EntityNotFoundException;
import com.app.workshop_registration_system.Models.UserModel;
import com.app.workshop_registration_system.Repositories.UserRepository;

@Service
public class AuthenticatedUserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel getCurrentUser(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(username).orElseThrow(()->new EntityNotFoundException("Usuario autenticado no encontrado"));
    }

    public Long getCurrentUserId(){
        return getCurrentUser().getId();
    }

    public Boolean isCurrentUser(Long userId){
        return getCurrentUserId().equals(userId);
    }

}
