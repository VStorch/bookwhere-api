package com.viniciusstorch.bookwhere_api.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LibraryRegisterDTO(
    
    @NotBlank(message = "Library name is required")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email,
    
    @NotBlank(message = "Password is required") 
    String password
) {
}
