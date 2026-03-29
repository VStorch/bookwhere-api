package com.viniciusstorch.bookwhere_api.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciusstorch.bookwhere_api.dtos.request.LoginRequestDTO;
import com.viniciusstorch.bookwhere_api.models.Account;
import com.viniciusstorch.bookwhere_api.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Account> login(LoginRequestDTO loginRequest) {
        return accountRepository.findByEmail(loginRequest.email())
        .filter(account -> passwordEncoder.matches(loginRequest.password(), account.getPassword()));
    }
}
