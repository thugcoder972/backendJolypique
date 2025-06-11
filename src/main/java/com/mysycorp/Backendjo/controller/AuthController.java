// package com.mysycorp.Backendjo.controller;

// import java.time.Instant;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.UUID;

// import org.springframework.security.core.AuthenticationException;

// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.mysycorp.Backendjo.dto.AuthRequest;
// import com.mysycorp.Backendjo.service.AuthService;
// import com.mysycorp.Backendjo.util.JwtTokenUtil;

// @RestController
// @RequestMapping("/api")
// public class AuthController {

//     private final AuthService authService;
//     private final JwtTokenUtil jwtTokenUtil;
//     private final AuthenticationManager authenticationManager;

//     public AuthController(AuthService authService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
//         this.authService = authService;
//         this.jwtTokenUtil = jwtTokenUtil;
//         this.authenticationManager = authenticationManager;
//     }

//     @PostMapping("/register")
//     public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
//         try {
//             // Vérifiez si l'email est nul ou vide, sinon générez un email unique
//             String email = (null == authRequest.getEmail() || authRequest.getEmail().isEmpty()) 
//                 ? generateUniqueEmail() 
//                 : authRequest.getEmail();
            
//             // Enregistrez l'utilisateur avec l'email
//             authService.register(authRequest.getUsername(), authRequest.getPassword(), email);
//             return ResponseEntity.ok("Utilisateur enregistré avec succès.");
//         } catch (RuntimeException e) {
//             return ResponseEntity.badRequest().body(e.getMessage());
//         }
//     }

    
//     @PostMapping("/login")
// public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
//     try {
//         authenticationManager.authenticate(
//             new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//         );

//         UserDetails userDetails = authService.loadUserByUsername(authRequest.getUsername());
//         String token = jwtTokenUtil.generateToken(userDetails);

//         // Retournez un JSON structuré au lieu d'une String
//         Map<String, String> response = new HashMap<>();
//         response.put("token", token);
//         response.put("message", "Authentication successful");
        
//         return ResponseEntity.ok(response); // Format: {"token": "eyJ...", "message": "..."}
        
//     } catch (AuthenticationException e) {
//         Map<String, String> errorResponse = new HashMap<>();
//         errorResponse.put("error", "Invalid username/password");
//         return ResponseEntity.status(401).body(errorResponse);
//     }
// }

//     public String generateUniqueEmail() {
//         String randomId = UUID.randomUUID().toString();
//         long timestamp = Instant.now().toEpochMilli();
//         return "user+" + randomId + timestamp + "@example.com";
//     }
// }


package com.mysycorp.Backendjo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysycorp.Backendjo.dto.AuthRequest;
import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.service.AuthService;
import com.mysycorp.Backendjo.util.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, JwtTokenUtil jwtTokenUtil, 
                         AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> registrationData) {
        System.out.println("[AuthController] Requête d'inscription reçue");
        System.out.println("[AuthController] Données reçues: " + registrationData.toString());

        try {
            // Validation
            if (registrationData.get("username") == null || registrationData.get("username").isEmpty() ||
                registrationData.get("password") == null || registrationData.get("password").isEmpty()) {
                System.out.println("[AuthController] Validation échouée: username/password manquants");
                return ResponseEntity.badRequest().body(
                    Map.of("error", "Username and password are required")
                );
            }

            // Enregistrement
            System.out.println("[AuthController] Tentative d'enregistrement...");
            authService.register(
                registrationData.get("username"),
                registrationData.get("password"),
                registrationData.get("email"),
                registrationData.get("tel"),
                registrationData.get("type")
            );

            System.out.println("[AuthController] Enregistrement réussi");
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User registered successfully"
            ));
            
        } catch (RuntimeException e) {
            System.out.println("[AuthController] Erreur lors de l'enregistrement: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        System.out.println("[AuthController] Requête de connexion reçue pour: " + authRequest.getUsername());

        try {
            // Authentification
            System.out.println("[AuthController] Tentative d'authentification...");
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), 
                    authRequest.getPassword()
                )
            );

            // Récupération user
            System.out.println("[AuthController] Authentification réussie, génération du token...");
            User user = authService.authenticateAndGetUser(
                authRequest.getUsername(), 
                authRequest.getPassword()
            );
            
            // Génération token
            UserDetails userDetails = authService.loadUserByUsername(authRequest.getUsername());
            String token = jwtTokenUtil.generateToken(userDetails, user.getId());

            // Réponse
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", user.getId());
            response.put("username", user.getUsername());
            response.put("message", "Login successful");

            System.out.println("[AuthController] Connexion réussie pour user ID: " + user.getId());
            return ResponseEntity.ok(response);
            
        } catch (AuthenticationException e) {
            System.out.println("[AuthController] Échec de l'authentification: " + e.getMessage());
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }
}