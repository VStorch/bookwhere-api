package com.viniciusstorch.bookwhere_api.library.mapper;

import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryRegisterDTO;
import com.viniciusstorch.bookwhere_api.library.model.Library;

public class LibraryMapper {

    public static Library toEntity(LibraryRegisterDTO libraryRegisterDTO) {
        Library libraryEntity = new Library();
        libraryEntity.setName(libraryRegisterDTO.name());
        libraryEntity.setEmail(libraryRegisterDTO.email());
        libraryEntity.setPassword(libraryRegisterDTO.password());
        libraryEntity.setPhone(libraryRegisterDTO.phone());
        libraryEntity.setLatitude(libraryRegisterDTO.latitude());
        libraryEntity.setLongitude(libraryRegisterDTO.longitude()); 
        return libraryEntity;
    }
}
