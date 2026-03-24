package com.viniciusstorch.bookwhere_api.mappers;

import com.viniciusstorch.bookwhere_api.dtos.request.UserRegisterDTO;
import com.viniciusstorch.bookwhere_api.models.User;

public class UserMapper {
    
    public static User toEntity(UserRegisterDTO userRegisterDTO) {
        User userEntity = new User();
        userEntity.setName(userRegisterDTO.name());
        userEntity.setEmail(userRegisterDTO.email());
        userEntity.setPassword(userRegisterDTO.password());
        return userEntity;
    }
}
