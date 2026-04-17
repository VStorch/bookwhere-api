package com.viniciusstorch.bookwhere_api.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.book.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    
}
