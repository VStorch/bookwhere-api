package com.viniciusstorch.bookwhere_api.models;

import com.viniciusstorch.bookwhere_api.models.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class User extends Account {
    public User() {
        super();
        this.setRole(Role.ROLE_USER);
    }
}