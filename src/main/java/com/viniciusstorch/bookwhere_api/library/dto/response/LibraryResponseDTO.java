package com.viniciusstorch.bookwhere_api.library.dto.response;

public record LibraryResponseDTO(
    Long id,
    String name,
    String email,
    String phone,
    AddressResponseDTO address
) {
}
