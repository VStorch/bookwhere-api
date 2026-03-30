package com.viniciusstorch.bookwhere_api.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciusstorch.bookwhere_api.dtos.request.UserRegisterDTO;
import com.viniciusstorch.bookwhere_api.mappers.UserMapper;
import com.viniciusstorch.bookwhere_api.models.User;
import com.viniciusstorch.bookwhere_api.repositories.AccountRepository;
import com.viniciusstorch.bookwhere_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<User> registerUser(UserRegisterDTO userRegisterDTO) {
        if (emailAlreadyExists(userRegisterDTO.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User userEntity = UserMapper.toEntity(userRegisterDTO);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);
        return Optional.of(userEntity);
    }


    private boolean emailAlreadyExists(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }
}
