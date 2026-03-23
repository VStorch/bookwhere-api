package com.viniciusstorch.bookwhere_api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "libraries")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Library extends Account {
    
    private String phone;
    private Double latitude;
    private Double longitude;
}
