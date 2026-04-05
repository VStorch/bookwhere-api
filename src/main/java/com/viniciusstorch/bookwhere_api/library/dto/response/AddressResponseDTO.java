package com.viniciusstorch.bookwhere_api.library.dto.response;

public record AddressResponseDTO(
    Long id,
    String state,
    String city,
    String neighborhood,
    String street,
    String number,
    String addressComplement
) {
}