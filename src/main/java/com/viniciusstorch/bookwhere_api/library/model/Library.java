package com.viniciusstorch.bookwhere_api.library.model;

import java.util.ArrayList;
import java.util.List;

import com.viniciusstorch.bookwhere_api.account.model.Account;
import com.viniciusstorch.bookwhere_api.account.model.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibraryHours> hours = new ArrayList<>();

    public Library() {
        super();
        this.setRole(Role.ROLE_LIBRARY);
    }
}
