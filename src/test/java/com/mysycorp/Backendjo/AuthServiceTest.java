package com.mysycorp.Backendjo;

import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.repository.UserRepository;
import com.mysycorp.Backendjo.service.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock  
    private UserRepository userRepository;

    @Mock  
    private PasswordEncoder passwordEncoder;

    @InjectMocks  
    private AuthService authService;

    private User user;

    @BeforeEach  
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("test@example.com");
    }

    @Test  
    public void testRegister_Success() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = authService.register(user.getUsername(), user.getPassword(), user.getEmail());

        assertNotNull(registeredUser);
        assertEquals(user.getUsername(), registeredUser.getUsername());
        assertEquals(user.getEmail(), registeredUser.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test  
    public void testRegister_UsernameAlreadyExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(user.getUsername(), user.getPassword(), user.getEmail());
        });

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test  
    public void testRegister_EmailAlreadyExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(user.getUsername(), user.getPassword(), user.getEmail());
        });

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test  
    public void testAuthenticate_Success() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);

        User authenticatedUser = authService.authenticate(user.getUsername(), user.getPassword());

        assertNotNull(authenticatedUser);
        assertEquals(user.getUsername(), authenticatedUser.getUsername());
    }

    @Test  
    public void testAuthenticate_InvalidUsername() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.authenticate(user.getUsername(), user.getPassword());
        });

        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test  
    public void testAuthenticate_InvalidPassword() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.authenticate(user.getUsername(), user.getPassword());
        });

        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test  
    public void testLoadUserByUsername_UserFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        UserDetails userDetails = authService.loadUserByUsername(user.getUsername());

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    @Test  
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            authService.loadUserByUsername(user.getUsername());
        });
    }
}