package com.viniciusstorch.bookwhere_api.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.book.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}
