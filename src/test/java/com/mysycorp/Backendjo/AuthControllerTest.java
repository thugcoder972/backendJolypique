package com.mysycorp.Backendjo; // Correspond au chemin du fichier

import com.mysycorp.Backendjo.controller.AuthController; // Import explicite
import com.mysycorp.Backendjo.dto.AuthRequest;
import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.service.AuthService;
import com.mysycorp.Backendjo.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    private AuthRequest authRequest;
    private User mockUser;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        authRequest = new AuthRequest();
        authRequest.setUsername("testUser");
        authRequest.setPassword("password");
        authRequest.setEmail("test@example.com");
        
        mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("encodedPassword");
        mockUser.setEmail("test@example.com");
        
        UserBuilder builder = org.springframework.security.core.userdetails.User.builder();
        userDetails = builder
            .username("testUser")
            .password("encodedPassword")
            .roles("USER")
            .build();
    }

    @Test
    void register_Success() {
        when(authService.register(anyString(), anyString(), anyString()))
            .thenReturn(mockUser);

        ResponseEntity<?> response = authController.register(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Utilisateur enregistré avec succès.", response.getBody());
    }

    @Test
    void login_Success() {
        when(authenticationManager.authenticate(any()))
            .thenReturn(new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()));
        
        when(authService.loadUserByUsername(anyString()))
            .thenReturn(userDetails);
            
        when(jwtTokenUtil.generateToken(any(UserDetails.class)))
            .thenReturn("fake-jwt-token");

        ResponseEntity<?> response = authController.login(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Token : fake-jwt-token", response.getBody());
    }

    @Test
    void login_InvalidCredentials() {
        when(authenticationManager.authenticate(any()))
            .thenThrow(new RuntimeException("Bad credentials"));

        ResponseEntity<?> response = authController.login(authRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Bad credentials"));
    }

    @Test
    void generateUniqueEmail_ShouldReturnValidFormat() {
        String email = authController.generateUniqueEmail();
        assertNotNull(email);
        assertTrue(email.matches("^user\\+[a-f0-9-]+\\d+@example\\.com$"));
    }
}