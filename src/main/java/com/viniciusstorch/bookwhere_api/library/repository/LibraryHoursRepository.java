package com.viniciusstorch.bookwhere_api.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.library.model.LibraryHour;

public interface LibraryHoursRepository extends JpaRepository<LibraryHour, Long> {
}
