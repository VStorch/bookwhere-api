package com.viniciusstorch.bookwhere_api.library.mapper;

import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryHourRequestDTO;
import com.viniciusstorch.bookwhere_api.library.dto.response.LibraryHourResponseDTO;
import com.viniciusstorch.bookwhere_api.library.model.LibraryHour;

public class LibraryHourMapper {
    
    public static LibraryHour toEntity(LibraryHourRequestDTO libraryHourDTO) {
        LibraryHour libraryHourEntity = new LibraryHour();
        libraryHourEntity.setWeekDay(libraryHourDTO.weekDay());
        libraryHourEntity.setOpeningTime(libraryHourDTO.openingTime());
        libraryHourEntity.setClosingTime(libraryHourDTO.closingTime());
        return libraryHourEntity;
    }

    public static LibraryHourResponseDTO toDTO(LibraryHour libraryHour) {
        return new LibraryHourResponseDTO(
            libraryHour.getId(),
            libraryHour.getWeekDay(),
            libraryHour.getOpeningTime(),
            libraryHour.getClosingTime()
        );
    }
}
