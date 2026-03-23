package com.viniciusstorch.bookwhere_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viniciusstorch.bookwhere_api.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
}
