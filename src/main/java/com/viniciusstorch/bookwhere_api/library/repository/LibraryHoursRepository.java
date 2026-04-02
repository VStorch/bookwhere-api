package com.viniciusstorch.bookwhere_api.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.library.model.LibraryHours;

public interface LibraryHoursRepository extends JpaRepository<LibraryHours, Long> {
}
