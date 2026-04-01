package com.viniciusstorch.bookwhere_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.viniciusstorch.bookwhere_api.account.model.Role;
import com.viniciusstorch.bookwhere_api.account.repository.AccountRepository;
import com.viniciusstorch.bookwhere_api.user.dto.request.UserRegisterDTO;
import com.viniciusstorch.bookwhere_api.user.model.User;
import com.viniciusstorch.bookwhere_api.user.repository.UserRepository;
import com.viniciusstorch.bookwhere_api.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should register a new user successfully")
    void testRegisterUser() {
        UserRegisterDTO dto = new UserRegisterDTO("João", "joao@example.com", "123456");

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword("encodedPassword");

        when(accountRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);

        Optional<User> result = userService.registerUser(dto);

        assertTrue(result.isPresent());
        assertEquals("João", result.get().getName());
        assertEquals("encodedPassword", result.get().getPassword());

        verify(accountRepository).findByEmail(dto.email());
        verify(passwordEncoder).encode(dto.password());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals(Role.ROLE_USER, savedUser.getRole());
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        UserRegisterDTO dto = new UserRegisterDTO("Maria", "maria@example.com", "123456");
        when(accountRepository.findByEmail(dto.email())).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(dto);
        });

        verify(userRepository, never()).save(any());
    }
}
