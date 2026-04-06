package com.viniciusstorch.bookwhere_api.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LibraryUpdateDTO(
    
    @NotBlank(message = "Library name is required")
    String name,

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\d{10,11}$", message = "Phone must contain 10 or 11 digits")
    String phone,

    @NotNull(message = "Latitude is required")
    Double latitude,

    @NotNull(message = "Longitude is required")
    Double longitude,

    AddressRequestDTO address
) {
}
