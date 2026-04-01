package com.viniciusstorch.bookwhere_api.library.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciusstorch.bookwhere_api.account.repository.AccountRepository;
import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryRegisterDTO;
import com.viniciusstorch.bookwhere_api.library.mapper.LibraryMapper;
import com.viniciusstorch.bookwhere_api.library.model.Library;
import com.viniciusstorch.bookwhere_api.library.repository.LibraryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryService {
    
    private final LibraryRepository libraryRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<Library> registerLibrary(LibraryRegisterDTO libraryRegisterDTO) {
        if (emailAlreadyExists(libraryRegisterDTO.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Library libraryEntity = LibraryMapper.toEntity(libraryRegisterDTO);
        libraryEntity.setPassword(passwordEncoder.encode(libraryEntity.getPassword()));

        return Optional.of(libraryRepository.save(libraryEntity));
    }

    
    private boolean emailAlreadyExists(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }
}
