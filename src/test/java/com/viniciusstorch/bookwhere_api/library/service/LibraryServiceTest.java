package com.viniciusstorch.bookwhere_api.library.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.viniciusstorch.bookwhere_api.account.repository.AccountRepository;
import com.viniciusstorch.bookwhere_api.library.dto.request.AddressRequestDTO;
import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryRegisterDTO;
import com.viniciusstorch.bookwhere_api.library.model.Address;
import com.viniciusstorch.bookwhere_api.library.model.Library;
import com.viniciusstorch.bookwhere_api.library.repository.LibraryRepository;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {
    
    @Mock
    LibraryRepository libraryRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    LibraryService libraryService;

    @Test
    @DisplayName("Should register a new library successfully")
    void shouldRegisterLibrarySuccessfully() {

        AddressRequestDTO addressDTO = new AddressRequestDTO(
            "Estado Exemplo",
            "Cidade Exemplo",
            "Bairro Exemplo",
            "Rua Exemplo",
            "123",
            "Complemento"
        );

        LibraryRegisterDTO dto = new LibraryRegisterDTO(
            "Biblioteca Central",
            "email@example.com",
            "password123",
            "1234567890",
            -23.55052,
            -46.633308,
            addressDTO
        );

        Library savedLibrary = new Library();
        savedLibrary.setName(dto.name());
        savedLibrary.setEmail(dto.email());
        savedLibrary.setPassword("encodedPassword");
        savedLibrary.setAddress(
            new Address() {{
                setState(addressDTO.state());
                setCity(addressDTO.city());
                setNeighborhood(addressDTO.neighborhood());
                setStreet(addressDTO.street());
                setNumber(addressDTO.number());
                setAddressComplement(addressDTO.addressComplement());
            }}
        );
        
        when(accountRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPassword");
        when(libraryRepository.save(any())).thenReturn(savedLibrary);

        var result = libraryService.registerLibrary(dto);

        assertTrue(result != null);
        assertTrue(result.name().equals(dto.name()));
        assertTrue(result.email().equals(dto.email()));
    }
}
