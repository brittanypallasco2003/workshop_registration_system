package com.app.workshop_registration_system.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.workshop_registration_system.Models.UserModel;


public interface UserRepository extends CrudRepository<UserModel, Long> {

    Optional<UserModel> findByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

}
