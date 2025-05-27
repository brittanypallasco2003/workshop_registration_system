package com.app.workshop_registration_system.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.app.workshop_registration_system.Models.WorkshopModel;

public interface WorkshopRepository extends CrudRepository<WorkshopModel, Long> {

}
