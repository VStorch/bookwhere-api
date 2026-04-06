package com.viniciusstorch.bookwhere_api.library.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciusstorch.bookwhere_api.account.repository.AccountRepository;
import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryHourRequestDTO;
import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryRegisterDTO;
import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryUpdateDTO;
import com.viniciusstorch.bookwhere_api.library.dto.response.LibraryHourResponseDTO;
import com.viniciusstorch.bookwhere_api.library.dto.response.LibraryResponseDTO;
import com.viniciusstorch.bookwhere_api.library.dto.response.MyLibraryResponseDTO;
import com.viniciusstorch.bookwhere_api.library.mapper.LibraryHourMapper;
import com.viniciusstorch.bookwhere_api.library.mapper.LibraryMapper;
import com.viniciusstorch.bookwhere_api.library.model.Library;
import com.viniciusstorch.bookwhere_api.library.model.LibraryHour;
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
        if (emailAlreadyExists(libraryRegisterDTO.email()))
            throw new IllegalArgumentException("Email already exists");

        Library libraryEntity = LibraryMapper.toEntity(libraryRegisterDTO);
        libraryEntity.setPassword(passwordEncoder.encode(libraryEntity.getPassword()));

        return Optional.of(libraryRepository.save(libraryEntity));
    }

    private boolean emailAlreadyExists(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }

    public MyLibraryResponseDTO getCurrentLibrary(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new IllegalArgumentException("Library not found"));

        return LibraryMapper.toMyLibraryResponse(library);
    }

    public List<LibraryResponseDTO> getAllLibraries() {
        return libraryRepository.findAll().stream()
            .map(LibraryMapper::toResponse)
            .toList();
    }

    @Transactional
    public LibraryResponseDTO updateLibrary(Long libraryId, LibraryUpdateDTO updateDTO) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new IllegalArgumentException("Library not found"));
        LibraryMapper.updateEntityFromDTO(library, updateDTO);
        return LibraryMapper.toResponse(libraryRepository.save(library));
    }

    @Transactional
    public void deleteLibrary(Long libraryId) {
        if (!libraryRepository.existsById(libraryId))
            throw new IllegalArgumentException("Library not found");
        libraryRepository.deleteById(libraryId);
    }


    @Transactional
    public void addLibraryHour(Long libraryId, LibraryHourRequestDTO hourDTO) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new IllegalArgumentException("Library not found"));

        validateTimeRange(hourDTO.openingTime(), hourDTO.closingTime());
        checkTimeOverlap(library, hourDTO, null);

        LibraryHour newHour = LibraryHourMapper.toEntity(hourDTO);
        library.addHour(newHour);
    }

    public List<LibraryHourResponseDTO> getLibraryHours(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new IllegalArgumentException("Library not found"));
        return library.getHours().stream()
            .map(LibraryHourMapper::toDTO)
            .toList();
    }

    @Transactional
    public void updateLibraryHour(Long libraryId, Long hourId, LibraryHourRequestDTO updateHourDTO) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new IllegalArgumentException("Library not found"));

        validateTimeRange(updateHourDTO.openingTime(), updateHourDTO.closingTime());
        checkTimeOverlap(library, updateHourDTO, hourId);

        LibraryHour existingHour = library.getHours().stream()
            .filter(hour -> hour.getId().equals(hourId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Hour not found"));

        existingHour.setWeekDay(updateHourDTO.weekDay());
        existingHour.setOpeningTime(updateHourDTO.openingTime());
        existingHour.setClosingTime(updateHourDTO.closingTime());
    }

    @Transactional
    public void removeLibraryHour(Long libraryId, Long hourId) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new IllegalArgumentException("Library not found"));
        
        boolean removed = library.getHours().removeIf(hour -> hour.getId().equals(hourId));
        if (!removed)
            throw new IllegalArgumentException("Hour not found");
    }

    private void validateTimeRange(LocalTime openingTime, LocalTime closingTime) {
        if (!closingTime.isAfter(openingTime))
            throw new IllegalArgumentException("Closing time cannot be before opening time");
    }

    private void checkTimeOverlap(Library library, LibraryHourRequestDTO hourDTO, Long hourIdToIgnore) {
        boolean hasOverlap = library.getHours().stream()
            .filter(hour -> hour.getWeekDay().equals(hourDTO.weekDay()))
            .filter(hour -> !Objects.equals(hour.getId(), hourIdToIgnore))
            .anyMatch(hour -> 
                hour.getOpeningTime().isBefore(hourDTO.closingTime()) &&
                hourDTO.openingTime().isBefore(hour.getClosingTime())
            );
        if (hasOverlap)
            throw new IllegalArgumentException("Time range overlaps with an existing hour");
    }
}
