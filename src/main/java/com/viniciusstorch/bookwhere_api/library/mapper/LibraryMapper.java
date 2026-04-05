package com.viniciusstorch.bookwhere_api.library.mapper;

import com.viniciusstorch.bookwhere_api.library.dto.request.LibraryRegisterDTO;
import com.viniciusstorch.bookwhere_api.library.dto.response.LibraryResponseDTO;
import com.viniciusstorch.bookwhere_api.library.dto.response.MyLibraryResponseDTO;
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
        libraryEntity.setAddress(AddressMapper.toEntity(libraryRegisterDTO.address()));
        return libraryEntity;
    }

    public static LibraryResponseDTO toResponse(Library library) {
        return new LibraryResponseDTO(
            library.getId(),
            library.getName(),
            library.getEmail(),
            library.getPhone(),
            AddressMapper.toResponse(library.getAddress()));
    }

    public static MyLibraryResponseDTO toMyLibraryResponse(Library library) {
        return new MyLibraryResponseDTO(
            library.getId(),
            library.getName(),
            library.getEmail(),
            library.getPhone(),
            library.getLatitude(),
            library.getLongitude(),
            AddressMapper.toResponse(library.getAddress()));
    }
}
