package com.viniciusstorch.bookwhere_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
