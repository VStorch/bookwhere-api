package com.viniciusstorch.bookwhere_api.account.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciusstorch.bookwhere_api.account.dto.request.AuthRequestDTO;
import com.viniciusstorch.bookwhere_api.account.dto.response.AuthResponseDTO;
import com.viniciusstorch.bookwhere_api.account.dto.response.MeResponseDTO;
import com.viniciusstorch.bookwhere_api.account.mapper.AuthMapper;
import com.viniciusstorch.bookwhere_api.account.model.Account;
import com.viniciusstorch.bookwhere_api.account.repository.AccountRepository;
import com.viniciusstorch.bookwhere_api.security.details.CustomUserDetails;
import com.viniciusstorch.bookwhere_api.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDTO login(AuthRequestDTO loginRequest) {
        Account account = accountRepository.findByEmail(loginRequest.email())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.password(), account.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(account);

        return AuthMapper.toAuthResponseDTO(token, account);
    }

    public MeResponseDTO getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new RuntimeException("User not authenticated");
        }
        
        return new MeResponseDTO(
            userDetails.id(),
            userDetails.name(),
            userDetails.email(),
            userDetails.role()
        );
    }
}
