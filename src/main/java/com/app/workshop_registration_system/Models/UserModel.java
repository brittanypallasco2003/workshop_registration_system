package com.app.workshop_registration_system.Models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    @Column(unique = true)
    private String email;

    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleModel roleModel;

    @OneToMany(mappedBy = "user",orphanRemoval = true)
    private Set<RegistrationModel> registrations;

    @Column(name = "is_enabled")
    private boolean isEnable;

    @Column(name = "account_No_expired")
    private boolean accountNoExpired;
    @Column(name = "account_No_locked")
    private boolean accountNoLocked;
    @Column(name = "credential_No_expired")
    private boolean credentialNoExpired;
  

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public UserModel() {
        this.registrations= new HashSet<>();
    }

    @Builder
    public UserModel(String name, String lastname, String email, String password, String phoneNumber,
            RoleModel roleModel, boolean isEnable, boolean accountNoExpired, boolean accountNoLocked,
            boolean credentialNoExpired) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roleModel = roleModel;
        this.isEnable = isEnable;
        this.accountNoExpired = accountNoExpired;
        this.accountNoLocked = accountNoLocked;
        this.credentialNoExpired = credentialNoExpired;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserModel other = (UserModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UserModel addRegistration(RegistrationModel registration){
        registrations.add(registration);
        registration.setUser(this);
        return this;
    }

}
