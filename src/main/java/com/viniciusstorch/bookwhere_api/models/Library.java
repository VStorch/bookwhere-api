package com.viniciusstorch.bookwhere_api.models;

import com.viniciusstorch.bookwhere_api.models.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "libraries")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Library extends Account {
    
    private String phone;
    private Double latitude;
    private Double longitude;

    public Library() {
        super();
        this.setRole(Role.ROLE_LIBRARY);
    }
}
