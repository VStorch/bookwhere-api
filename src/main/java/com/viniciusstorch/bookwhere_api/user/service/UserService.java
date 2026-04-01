package com.viniciusstorch.bookwhere_api.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viniciusstorch.bookwhere_api.account.repository.AccountRepository;
import com.viniciusstorch.bookwhere_api.user.dto.request.UserRegisterDTO;
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
    public Optional<User> registerUser(UserRegisterDTO userRegisterDTO) {
        if (emailAlreadyExists(userRegisterDTO.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User userEntity = UserMapper.toEntity(userRegisterDTO);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        return Optional.of(userRepository.save(userEntity));
    }


    private boolean emailAlreadyExists(String email) {
        return accountRepository.findByEmail(email).isPresent();
    }
}
