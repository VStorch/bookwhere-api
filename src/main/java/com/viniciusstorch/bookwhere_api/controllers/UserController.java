package com.viniciusstorch.bookwhere_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viniciusstorch.bookwhere_api.dtos.request.UserRegisterDTO;
import com.viniciusstorch.bookwhere_api.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        userService.registerUser(userRegisterDTO);
        return ResponseEntity.ok().build();
    }
}
