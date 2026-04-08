package com.viniciusstorch.bookwhere_api.book.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "book_editions")
public class BookEdition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String isbn;

    LocalDate publicationYear;

    String image;

    @ManyToOne
    @JoinColumn(name = "work_id", nullable = false)
    private Work work;
}
