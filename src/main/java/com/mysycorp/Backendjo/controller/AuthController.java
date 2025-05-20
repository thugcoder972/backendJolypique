package com.mysycorp.Backendjo.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.AuthenticationException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysycorp.Backendjo.dto.AuthRequest;
import com.mysycorp.Backendjo.service.AuthService;
import com.mysycorp.Backendjo.util.JwtTokenUtil;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        try {
            // Vérifiez si l'email est nul ou vide, sinon générez un email unique
            String email = (null == authRequest.getEmail() || authRequest.getEmail().isEmpty()) 
                ? generateUniqueEmail() 
                : authRequest.getEmail();
            
            // Enregistrez l'utilisateur avec l'email
            authService.register(authRequest.getUsername(), authRequest.getPassword(), email);
            return ResponseEntity.ok("Utilisateur enregistré avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
    //     try {
    //         // Authentifier l'utilisateur
    //         authenticationManager.authenticate(
    //                 new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
    //         );

    //         // Charger les UserDetails après l'authentification
    //         UserDetails userDetails = authService.loadUserByUsername(authRequest.getUsername());

    //         // Générer le token JWT à partir des UserDetails
    //         String token = jwtTokenUtil.generateToken(userDetails);

    //         // Renvoyer le token dans la réponse
    //         return ResponseEntity.ok("Token : " + token);
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }
    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
    try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetails userDetails = authService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        // Retournez un JSON structuré au lieu d'une String
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "Authentication successful");
        
        return ResponseEntity.ok(response); // Format: {"token": "eyJ...", "message": "..."}
        
    } catch (AuthenticationException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Invalid username/password");
        return ResponseEntity.status(401).body(errorResponse);
    }
}

    public String generateUniqueEmail() {
        String randomId = UUID.randomUUID().toString();
        long timestamp = Instant.now().toEpochMilli();
        return "user+" + randomId + timestamp + "@example.com";
    }
}


