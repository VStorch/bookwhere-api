package com.viniciusstorch.bookwhere_api.library.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciusstorch.bookwhere_api.account.repository.AccountRepository;
import com.viniciusstorch.bookwhere_api.exception.custom.BusinessException;
import com.viniciusstorch.bookwhere_api.exception.custom.ResourceNotFoundException;
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
    public LibraryResponseDTO registerLibrary(LibraryRegisterDTO libraryRegisterDTO) {
        if (emailAlreadyExists(libraryRegisterDTO.email()))
            throw new BusinessException("Email already exists");

        Library libraryEntity = LibraryMapper.toEntity(libraryRegisterDTO);
        libraryEntity.setPassword(passwordEncoder.encode(libraryEntity.getPassword()));

        return LibraryMapper.toResponse(libraryRepository.save(libraryEntity));
    }

    private boolean emailAlreadyExists(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }

    public MyLibraryResponseDTO getCurrentLibrary(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new ResourceNotFoundException("Library not found"));

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
            .orElseThrow(() -> new ResourceNotFoundException("Library not found"));
        LibraryMapper.updateEntityFromDTO(library, updateDTO);
        return LibraryMapper.toResponse(libraryRepository.save(library));
    }

    @Transactional
    public void deleteLibrary(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new ResourceNotFoundException("Library not found"));
        libraryRepository.delete(library);
    }


    @Transactional
    public LibraryHourResponseDTO addLibraryHour(Long libraryId, LibraryHourRequestDTO hourDTO) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new ResourceNotFoundException("Library not found"));

        validateTimeRange(hourDTO.openingTime(), hourDTO.closingTime());
        checkTimeOverlap(library, hourDTO, null);

        LibraryHour newHour = LibraryHourMapper.toEntity(hourDTO);
        library.addHour(newHour);
        return LibraryHourMapper.toDTO(newHour);
    }

    public List<LibraryHourResponseDTO> getLibraryHours(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new ResourceNotFoundException("Library not found"));
        return library.getHours().stream()
            .map(LibraryHourMapper::toDTO)
            .toList();
    }

    @Transactional
    public LibraryHourResponseDTO updateLibraryHour(Long libraryId, Long hourId, LibraryHourRequestDTO updateHourDTO) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new ResourceNotFoundException("Library not found"));

        validateTimeRange(updateHourDTO.openingTime(), updateHourDTO.closingTime());
        checkTimeOverlap(library, updateHourDTO, hourId);

        LibraryHour existingHour = library.getHours().stream()
            .filter(hour -> hour.getId().equals(hourId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Library hour not found"));

        existingHour.setWeekDay(updateHourDTO.weekDay());
        existingHour.setOpeningTime(updateHourDTO.openingTime());
        existingHour.setClosingTime(updateHourDTO.closingTime());
        return LibraryHourMapper.toDTO(existingHour);
    }

    @Transactional
    public void removeLibraryHour(Long libraryId, Long hourId) {
        Library library = libraryRepository.findById(libraryId)
            .orElseThrow(() -> new ResourceNotFoundException("Library not found"));
        
        boolean removed = library.getHours().removeIf(hour -> hour.getId().equals(hourId));
        if (!removed)
            throw new ResourceNotFoundException("Library hour not found");
    }

    private void validateTimeRange(LocalTime openingTime, LocalTime closingTime) {
        if (!closingTime.isAfter(openingTime))
            throw new BusinessException("Closing time must be after opening time");
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
            throw new BusinessException("Time range overlaps with an existing hour");
    }
}
