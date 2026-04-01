package com.viniciusstorch.bookwhere_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.models.Library;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
