package com.viniciusstorch.bookwhere_api.account.dto.response;

public record MeResponseDTO(
    Long id,
    String name,
    String email,
    String role
) {
}
