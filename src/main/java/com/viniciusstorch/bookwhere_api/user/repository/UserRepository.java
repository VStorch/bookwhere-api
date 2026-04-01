package com.viniciusstorch.bookwhere_api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
