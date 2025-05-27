package com.app.workshop_registration_system.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
 * 
 * 
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Registrations",
uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","workshop_id"})
)
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

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public RegistrationModel(UserModel user, WorkshopModel workshop, LocalDateTime registrationDate,
            StatusEnum status) {
        this.user = user;
        this.workshop = workshop;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((workshop == null) ? 0 : workshop.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RegistrationModel other = (RegistrationModel) obj;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (workshop == null) {
            if (other.workshop != null)
                return false;
        } else if (!workshop.equals(other.workshop))
            return false;
        return true;
    }

    @PrePersist
    public void prePersist() {
        this.registrationDate = LocalDateTime.now();
    }
}
