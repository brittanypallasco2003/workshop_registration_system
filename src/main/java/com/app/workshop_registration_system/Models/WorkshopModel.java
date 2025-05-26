package com.app.workshop_registration_system.Models;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/*
 * Representa a un taller en el sistema
 * 
 * Esta entidad almacena información como: nombre, descripción, fecha de inicio, cupos disponibles, inscripciones, lugar donde se realiza y si está activo
 * 
 * Tiene una relación bidireccional OneToMany con RegistrationModel
 * 
 *  - `cascade = CascadeType.ALL`: al guardar, actualizar o eliminar un taller, los cambios se propagan automáticamente a las inscripciones asociadas.
 * 
 * - `orphanRemoval = true`: si una inscripción se elimina de la lista del Taller (y no está asociada a ningún otro), también se eliminará de la base de datos.
 * 
 */

@Entity
@Table(name = "Workshops")
@Getter
@Setter
public class WorkshopModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "available_places")
    private Integer availablePlaces;

    /*
     * Inscripciones realizadas a cada curso. Deben ser unicas
     * 
     * Esta relación es bidireccional. La entidad RegistrationModel tiene una propiedad `workshop` con la anotación `@ManyToOne`
     * 
     * - `mappedBy = "workshop" ` indica que la propiedad `workshop` en RegistrationModel es la dueña de la relación.
     * 
     * - `orphanRemoval = true` elimina automáticamente las inscripciones que se eliminen de esta lista. 
     */

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RegistrationModel> registrations;

    private String place;

    private boolean active;

    public WorkshopModel() {
        this.registrations= new HashSet<>();
    }

    public WorkshopModel(String name, String description, LocalDateTime startDate, Integer availablePlaces,
            String place, boolean active) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.availablePlaces = availablePlaces;
        this.place = place;
        this.active = active;
    }

}
