package com.viniciusstorch.bookwhere_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viniciusstorch.bookwhere_api.dtos.request.LibraryRegisterDTO;
import com.viniciusstorch.bookwhere_api.services.LibraryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/libraries")
@RequiredArgsConstructor
public class LibraryController {
    
    private final LibraryService libraryService;

    @PostMapping("/register")
    public ResponseEntity<?> registerLibrary(@RequestBody @Valid LibraryRegisterDTO libraryRegisterDTO) {
        libraryService.registerLibrary(libraryRegisterDTO);
        return ResponseEntity.ok().build();
    }
}
