package com.viniciusstorch.bookwhere_api.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.viniciusstorch.bookwhere_api.account.repository.AccountRepository;
import com.viniciusstorch.bookwhere_api.library.dto.request.AddressRequestDTO;
import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryHourRequestDTO;
import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryRegisterDTO;
import com.viniciusstorch.bookwhere_api.library.model.Address;
import com.viniciusstorch.bookwhere_api.library.model.Library;
import com.viniciusstorch.bookwhere_api.library.model.LibraryHour;
import com.viniciusstorch.bookwhere_api.library.model.WeekDay;
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

        ArgumentCaptor<Library> captor = ArgumentCaptor.forClass(Library.class);

        verify(libraryRepository).save(captor.capture());

        Library saved = captor.getValue();
        assertEquals("encodedPassword", saved.getPassword());

        assertTrue(result != null);
        assertEquals(dto.name(), result.name());
        assertEquals(dto.email(), result.email());

        verify(passwordEncoder).encode(dto.password());
    }

    @Test
    @DisplayName("Should add library hour successfully")
    void shouldAddLibraryHourSuccessfully() {

        Long libraryId = 1L;

        LibraryHourRequestDTO hourDTO = new LibraryHourRequestDTO(
            WeekDay.MONDAY,
            LocalTime.of(9, 0),
            LocalTime.of(17, 0)
        );

        Library library = new Library();
        library.setId(libraryId);
        library.setHours(new ArrayList<>());

        when(libraryRepository.findById(libraryId)).thenReturn(Optional.of(library));

        var result = libraryService.addLibraryHour(libraryId, hourDTO);

        assertTrue(result != null);
        assertEquals(WeekDay.MONDAY, result.weekDay());
        assertEquals(LocalTime.of(9, 0), result.openingTime());
        assertEquals(LocalTime.of(17, 0), result.closingTime());

        assertTrue(library.getHours().size() == 1);
    }

    @Test
    @DisplayName("Should not add library hour with invalid time range")
    void shouldNotAddLibraryHourWithInvalidTimeRange() {

        Long libraryId = 1L;

        LibraryHourRequestDTO hourDTO = new LibraryHourRequestDTO(
            WeekDay.MONDAY,
            LocalTime.of(17, 0),
            LocalTime.of(9, 0)
        );

        Library library = new Library();
        library.setId(libraryId);
        library.setHours(new ArrayList<>());

        when(libraryRepository.findById(libraryId)).thenReturn(Optional.of(library));

        assertThrows(IllegalArgumentException.class, () -> libraryService.addLibraryHour(libraryId, hourDTO));
    }

    @Test
    @DisplayName("Should not add library hour with overlapping time")
    void shouldThrowExceptionWhenLibraryHourOverlaps() {

        Long libraryId = 1L;

        Library library = new Library();
        library.setId(libraryId);
        library.setHours(new ArrayList<>());

        LibraryHour existingHour = new LibraryHour();
        existingHour.setId(1L);
        existingHour.setWeekDay(WeekDay.MONDAY);
        existingHour.setOpeningTime(LocalTime.of(9, 0));
        existingHour.setClosingTime(LocalTime.of(17, 0));

        library.getHours().add(existingHour);

        LibraryHourRequestDTO hourDTO = new LibraryHourRequestDTO(
            WeekDay.MONDAY,
            LocalTime.of(10, 0),
            LocalTime.of(18, 0)
        );

        when(libraryRepository.findById(libraryId)).thenReturn(Optional.of(library));

        assertThrows(IllegalArgumentException.class, () -> libraryService.addLibraryHour(libraryId, hourDTO));
    }

    @Test
    @DisplayName("Should update library hour successfully")
    void shouldUpdateLibraryHourSuccessfully() {

        Long libraryId = 1L;
        Long hourId = 1L;

        Library library = new Library();
        library.setId(libraryId);
        library.setHours(new ArrayList<>());

        LibraryHour existingHour = new LibraryHour();
        existingHour.setId(hourId);
        existingHour.setWeekDay(WeekDay.MONDAY);
        existingHour.setOpeningTime(LocalTime.of(9, 0));
        existingHour.setClosingTime(LocalTime.of(17, 0));

        library.getHours().add(existingHour);

        LibraryHourRequestDTO updateHourDTO = new LibraryHourRequestDTO(
            WeekDay.MONDAY,
            LocalTime.of(10, 0),
            LocalTime.of(18, 0)
        );

        when(libraryRepository.findById(libraryId)).thenReturn(Optional.of(library));

        var result = libraryService.updateLibraryHour(libraryId, hourId, updateHourDTO);

        assertTrue(result != null);
        assertEquals(WeekDay.MONDAY, result.weekDay());
        assertEquals(LocalTime.of(10, 0), result.openingTime());
        assertEquals(LocalTime.of(18, 0), result.closingTime());
        assertEquals(LocalTime.of(10, 0), existingHour.getOpeningTime());
        assertEquals(LocalTime.of(18, 0), existingHour.getClosingTime());

        assertTrue(library.getHours().size() == 1);
    }
}
