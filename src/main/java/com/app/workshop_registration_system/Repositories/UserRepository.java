package com.app.workshop_registration_system.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.app.workshop_registration_system.Models.UserModel;

public interface UserRepository extends CrudRepository<UserModel, Long> {

}
