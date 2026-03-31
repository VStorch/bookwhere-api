package com.viniciusstorch.bookwhere_api.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciusstorch.bookwhere_api.dtos.request.LoginRequestDTO;
import com.viniciusstorch.bookwhere_api.models.Account;
import com.viniciusstorch.bookwhere_api.repositories.AccountRepository;
import com.viniciusstorch.bookwhere_api.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(LoginRequestDTO loginRequest) {
        Account account = accountRepository.findByEmail(loginRequest.email())
        .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.password(), account.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(account);
    }
}
