package com.viniciusstorch.bookwhere_api.library.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRequestDTO(

    @NotBlank(message = "State is required")
    String state,

    @NotBlank(message = "City is required")
    String city,
    
    @NotBlank(message = "Neighborhood is required")
    String neighborhood,
    
    @NotBlank(message = "Street is required")
    String street,

    @NotBlank(message = "Number is required")
    String number,
    
    String addressComplement
) {
}
