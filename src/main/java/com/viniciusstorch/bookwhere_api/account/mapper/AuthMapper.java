package com.viniciusstorch.bookwhere_api.account.mapper;

import com.viniciusstorch.bookwhere_api.account.dto.response.AuthResponseDTO;
import com.viniciusstorch.bookwhere_api.account.model.Account;

public class AuthMapper {
    
    public static AuthResponseDTO toAuthResponseDTO(String token, Account account) {
        return new AuthResponseDTO(
            token,
            account.getId(),
            account.getName(),
            account.getEmail(),
            account.getRole().name()
        );
    }
}
