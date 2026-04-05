package com.viniciusstorch.bookwhere_api.library.dto.response;

public record MyLibraryResponseDTO(
    Long id,
    String name,
    String email,
    String phone,
    Double latitude,
    Double longitude,
    AddressResponseDTO address
) {
}
