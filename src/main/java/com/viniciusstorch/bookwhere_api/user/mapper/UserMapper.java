package com.viniciusstorch.bookwhere_api.user.mapper;

import com.viniciusstorch.bookwhere_api.user.dto.request.UserRegisterDTO;
import com.viniciusstorch.bookwhere_api.user.model.User;

public class UserMapper {
    
    public static User toEntity(UserRegisterDTO userRegisterDTO) {
        User userEntity = new User();
        userEntity.setName(userRegisterDTO.name());
        userEntity.setEmail(userRegisterDTO.email());
        userEntity.setPassword(userRegisterDTO.password());
        return userEntity;
    }
}
