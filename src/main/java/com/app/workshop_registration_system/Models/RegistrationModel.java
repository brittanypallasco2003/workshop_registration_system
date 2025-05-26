package com.app.workshop_registration_system.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a inscripción en el sistema
 * 
 * Esta entidad almacena información como: usuario y taller inscrito, fecha de registro y estado que puede ser confirmado o cancelado
 * 
 * Tiene una relación bidireccional ManyToOne con con WorkshopModel
 * 
 * - Esta es la parte "dueña" de la relación (propietaria de la clave foránea).
 * 
 * - Cada inscripción pertenece a un solo taller
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Registrations")
public class RegistrationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    /**
     * Taller al que pertenece esta inscripción
     * 
     * - Es la parte dueña de la relación bidireccional con `WorkshopModel.registrations`
     * 
     * - Se crea una clave foránea en la tabla `Registrations` apuntando a `workshop.id`.
     */

    @ManyToOne
    @JoinColumn(name = "workshop_id")
    private WorkshopModel workshop;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    private StatusEnum status;

    public RegistrationModel(UserModel user, WorkshopModel workshop, LocalDateTime registrationDate,
            StatusEnum status) {
        this.user = user;
        this.workshop = workshop;
        this.registrationDate = registrationDate;
        this.status = status;
    }
}
