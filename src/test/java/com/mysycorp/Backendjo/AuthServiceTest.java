package com.mysycorp.Backendjo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.repository.UserRepository;
import com.mysycorp.Backendjo.service.AuthService;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_NewUser_ReturnsUser() {
        // Arrange
        when(userRepository.findByUsername("newuser")).thenReturn(null);
        when(userRepository.findByEmail("new@email.com")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User expectedUser = new User();
        expectedUser.setUsername("newuser");
        expectedUser.setPassword("encodedPassword");
        expectedUser.setEmail("new@email.com");
        expectedUser.setType("user");

        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        User result = authService.register("newuser", "password", "new@email.com");

        // Assert
        assertNotNull(result);
        assertEquals("newuser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void authenticateAndGetUser_ValidCredentials_ReturnsUser() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("encodedPassword");
        mockUser.setId(27L);

        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        // Act
        User result = authService.authenticateAndGetUser("testuser", "password");

        // Assert
        assertNotNull(result);
        assertEquals(27L, result.getId());
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(mockUser);

        // Act
        UserDetails result = authService.loadUserByUsername("testuser");

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            authService.loadUserByUsername("unknown");
        });
    }
}