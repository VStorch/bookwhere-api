package com.viniciusstorch.bookwhere_api.library.model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "library_hours")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;

    private LocalTime openingTime;
    private LocalTime closingTime;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;
}
