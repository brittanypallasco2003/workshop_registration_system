package com.app.workshop_registration_system.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.app.workshop_registration_system.Models.RegistrationModel;

public interface RegistrationRepository extends CrudRepository<RegistrationModel, Long> {
    @Query("Select r from RegistrationModel r JOIN FETCH r.workshop JOIN FETCH r.user where r.user.id = :userId AND r.workshop.id = :workshopId")
    Optional<RegistrationModel> buscarPorUsuarioYTaller(
            @Param("userId") Long userId,
            @Param("workshopId") Long workshopId);

    @Query("SELECT r FROM RegistrationModel r JOIN FETCH r.workshop JOIN FETCH r.user WHERE r.user.id = :userId")
    List<RegistrationModel> getRegistrationsByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM RegistrationModel r JOIN FETCH r.user JOIN FETCH r.workshop WHERE r.id = :id")
    Optional<RegistrationModel>findByIdWithUserAndWorkshop(@Param("id") Long id);

}
