package com.viniciusstorch.bookwhere_api.library.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryHourRequestDTO;
import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryRegisterDTO;
import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryUpdateDTO;
import com.viniciusstorch.bookwhere_api.library.dto.response.LibraryHourResponseDTO;
import com.viniciusstorch.bookwhere_api.library.dto.response.LibraryResponseDTO;
import com.viniciusstorch.bookwhere_api.library.dto.response.MyLibraryResponseDTO;
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
    public ResponseEntity<LibraryResponseDTO> registerLibrary(@RequestBody @Valid LibraryRegisterDTO libraryRegisterDTO) {
            return ResponseEntity
                .status(201)
                .body(libraryService.registerLibrary(libraryRegisterDTO));
    }

    @PreAuthorize("hasRole('LIBRARY')")
    @GetMapping("/me")
    public ResponseEntity<MyLibraryResponseDTO> getCurrentLibrary(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(libraryService.getCurrentLibrary(userDetails.id()));
    }

    @GetMapping
    public ResponseEntity<List<LibraryResponseDTO>> getAllLibraries() {
        return ResponseEntity.ok(libraryService.getAllLibraries());
    }

    @PreAuthorize("hasRole('LIBRARY')")
    @PutMapping("/me")
    public ResponseEntity<LibraryResponseDTO> updateLibrary(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid LibraryUpdateDTO updateDTO) {
        return ResponseEntity.ok(libraryService.updateLibrary(userDetails.id(), updateDTO));
    }

    @PreAuthorize("hasRole('LIBRARY')")
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteLibrary(@AuthenticationPrincipal CustomUserDetails userDetails) {
        libraryService.deleteLibrary(userDetails.id());
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasRole('LIBRARY')")
    @PostMapping("/hours")
    public ResponseEntity<LibraryHourResponseDTO> addLibraryHour(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid LibraryHourRequestDTO hourDTO) {
        return ResponseEntity.status(201).body(libraryService.addLibraryHour(userDetails.id(), hourDTO));
    }

    @PreAuthorize("hasRole('LIBRARY')")
    @GetMapping("/hours")
    public ResponseEntity<List<LibraryHourResponseDTO>> getLibraryHours(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(libraryService.getLibraryHours(userDetails.id()));
    }

    @PreAuthorize("hasRole('LIBRARY')")
    @PutMapping("/hours/{hourId}")
    public ResponseEntity<LibraryHourResponseDTO> updateLibraryHour(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long hourId, @RequestBody @Valid LibraryHourRequestDTO updateHourDTO) {
        return ResponseEntity.ok(libraryService.updateLibraryHour(userDetails.id(), hourId, updateHourDTO));
    }

    @PreAuthorize("hasRole('LIBRARY')")
    @DeleteMapping("/hours/{hourId}")
    public ResponseEntity<Void> removeLibraryHour(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long hourId) {
        libraryService.removeLibraryHour(userDetails.id(), hourId);
        return ResponseEntity.noContent().build();
    }
}
