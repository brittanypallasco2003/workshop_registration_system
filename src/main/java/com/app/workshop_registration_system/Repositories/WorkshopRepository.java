package com.app.workshop_registration_system.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.workshop_registration_system.Models.WorkshopModel;

public interface WorkshopRepository extends CrudRepository<WorkshopModel, Long> {

    List<WorkshopModel>findByActiveTrueAndAvailablePlacesGreaterThanEqual(Integer numMinPlaces);
}
