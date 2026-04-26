package com.viniciusstorch.bookwhere_api.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.book.model.Work;

public interface WorkRepository extends JpaRepository<Work, Long> {
    
}
