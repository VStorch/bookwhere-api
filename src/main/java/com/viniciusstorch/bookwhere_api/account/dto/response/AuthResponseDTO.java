package com.viniciusstorch.bookwhere_api.account.dto.response;

public record AuthResponseDTO(
    String token,
    Long id,
    String name,
    String email,
    String role
) {
}
