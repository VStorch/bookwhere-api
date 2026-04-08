package com.viniciusstorch.bookwhere_api.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciusstorch.bookwhere_api.account.repository.AccountRepository;
import com.viniciusstorch.bookwhere_api.exception.custom.BusinessException;
import com.viniciusstorch.bookwhere_api.user.dto.request.UserRegisterDTO;
import com.viniciusstorch.bookwhere_api.user.dto.response.UserResponseDTO;
import com.viniciusstorch.bookwhere_api.user.mapper.UserMapper;
import com.viniciusstorch.bookwhere_api.user.model.User;
import com.viniciusstorch.bookwhere_api.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO registerUser(UserRegisterDTO userRegisterDTO) {
        if (emailAlreadyExists(userRegisterDTO.email()))
            throw new BusinessException("Email already exists");

        User userEntity = UserMapper.toEntity(userRegisterDTO);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        return UserMapper.toDTO(userRepository.save(userEntity));
    }


    private boolean emailAlreadyExists(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }
}
