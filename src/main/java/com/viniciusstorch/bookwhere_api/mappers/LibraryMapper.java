package com.viniciusstorch.bookwhere_api.mappers;

import com.viniciusstorch.bookwhere_api.dtos.request.LibraryRegisterDTO;
import com.viniciusstorch.bookwhere_api.models.Library;

public class LibraryMapper {

    public static Library toEntity(LibraryRegisterDTO libraryRegisterDTO) {
        Library libraryEntity = new Library();
        libraryEntity.setName(libraryRegisterDTO.name());
        libraryEntity.setEmail(libraryRegisterDTO.email());
        libraryEntity.setPassword(libraryRegisterDTO.password());
        return libraryEntity;
    }
}
