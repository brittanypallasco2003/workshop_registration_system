package com.app.workshop_registration_system.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.workshop_registration_system.Models.RoleModel;

public interface RoleRepository extends CrudRepository<RoleModel, Long> {

    Optional<RoleModel> findByRoleEnum(String roleName);

}
