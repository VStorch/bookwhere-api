package com.viniciusstorch.bookwhere_api.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viniciusstorch.bookwhere_api.account.dto.request.AuthRequestDTO;
import com.viniciusstorch.bookwhere_api.account.dto.response.AuthResponseDTO;
import com.viniciusstorch.bookwhere_api.account.dto.response.MeResponseDTO;
import com.viniciusstorch.bookwhere_api.account.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO loginRequest) {
        AuthResponseDTO authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponseDTO> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
