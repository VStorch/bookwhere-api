package com.viniciusstorch.bookwhere_api.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.book.model.WorkAuthor;

public interface WorkAuthorRepository extends JpaRepository<WorkAuthor, Long> {
    
}
