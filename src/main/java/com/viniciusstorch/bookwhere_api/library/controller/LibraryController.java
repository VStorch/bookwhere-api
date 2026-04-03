package com.viniciusstorch.bookwhere_api.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryHourRequestDTO;
import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryRegisterDTO;
import com.viniciusstorch.bookwhere_api.library.service.LibraryService;
import com.viniciusstorch.bookwhere_api.security.details.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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


    @PreAuthorize("hasRole('LIBRARY')")
    @PostMapping("/hours")
    public ResponseEntity<?> addLibraryHour(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid LibraryHourRequestDTO hourDTO) {
        libraryService.addLibraryHour(userDetails.getId(), hourDTO);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('LIBRARY')")
    @GetMapping("/hours")
    public ResponseEntity<?> getLibraryHours(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(libraryService.getLibraryHours(userDetails.getId()));
    }

    @PreAuthorize("hasRole('LIBRARY')")
    @PutMapping("/hours/{hourId}")
    public ResponseEntity<?> updateLibraryHour(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long hourId, @RequestBody @Valid LibraryHourRequestDTO updateHourDTO) {
        libraryService.updateLibraryHour(userDetails.getId(), hourId, updateHourDTO);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('LIBRARY')")
    @DeleteMapping("/hours/{hourId}")
    public ResponseEntity<?> removeLibraryHour(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long hourId) {
        libraryService.removeLibraryHour(userDetails.getId(), hourId);
        return ResponseEntity.ok().build();
    }
}
