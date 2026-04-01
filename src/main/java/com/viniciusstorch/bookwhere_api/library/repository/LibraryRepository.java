package com.viniciusstorch.bookwhere_api.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.library.model.Library;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
