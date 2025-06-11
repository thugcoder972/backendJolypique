// package com.mysycorp.Backendjo.service;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.mysycorp.Backendjo.entity.User;
// import com.mysycorp.Backendjo.repository.UserRepository;

// @Service
// public class AuthService implements UserDetailsService {

//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;

//     public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     public User register(String username, String password, String email) { // Ajout de l'email en paramètre
//         if (userRepository.findByUsername(username) != null) {
//             throw new RuntimeException("Username already exists");
//         }
//         if (userRepository.findByEmail(email) != null) { // Vérifier si l'email existe déjà
//             throw new RuntimeException("Email already exists");
//         }

//         User user = new User();
//         user.setUsername(username);
//         user.setPassword(passwordEncoder.encode(password));
//         user.setEmail(email); // Définir l'email
//         user.setType("user"); // Type par défaut

//         return userRepository.save(user);
//     }

//     public User authenticate(String username, String password) {
//         User user = userRepository.findByUsername(username);
//         if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
//             throw new RuntimeException("Invalid username or password");
//         }
//         return user;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         User user = userRepository.findByUsername(username);
//         if (user == null) {
//             throw new UsernameNotFoundException("User not found");
//         }

//         // Construire et retourner un UserDetails à partir de l'entité User
//         return org.springframework.security.core.userdetails.User
//                 .withUsername(user.getUsername())
//                 .password(user.getPassword())
//                 .authorities("USER") // Tu peux ajouter des rôles ici si nécessaire
//                 .build();
//     }
// }

package com.mysycorp.Backendjo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysycorp.Backendjo.entity.User;
import com.mysycorp.Backendjo.repository.UserRepository;

import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String password, String email, String tel, String type) {
        System.out.println("[AuthService] Début de l'enregistrement - username: " + username);
        
        // Vérification username
        System.out.println("[AuthService] Vérification de l'existence du username...");
        if (userRepository.findByUsername(username) != null) {
            System.out.println("[AuthService] Erreur: Username existe déjà");
            throw new RuntimeException("Username already exists");
        }

        // Vérification email si fourni
        if (email != null && !email.isEmpty()) {
            System.out.println("[AuthService] Vérification de l'existence de l'email...");
            if (userRepository.findByEmail(email) != null) {
                System.out.println("[AuthService] Erreur: Email existe déjà");
                throw new RuntimeException("Email already exists");
            }
        } else {
            email = generateUniqueEmail();
            System.out.println("[AuthService] Génération d'un email unique: " + email);
        }

        // Création user
        System.out.println("[AuthService] Création du nouvel utilisateur...");
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setTel(tel != null ? tel : "");
        user.setType(type != null ? type : "user");
        user.setRoles("ROLE_USER");

        System.out.println("[AuthService] Utilisateur à enregistrer: " + user.toString());
        
        User savedUser = userRepository.save(user);
        System.out.println("[AuthService] Utilisateur enregistré avec ID: " + savedUser.getId());
        
        return savedUser;
    }

    private String generateUniqueEmail() {
        String email = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        System.out.println("[AuthService] Email généré: " + email);
        return email;
    }

    public User authenticateAndGetUser(String username, String password) {
        System.out.println("[AuthService] Authentification pour username: " + username);
        User user = userRepository.findByUsername(username);
        
        if (user == null) {
            System.out.println("[AuthService] Erreur: Utilisateur non trouvé");
            throw new RuntimeException("Invalid username or password");
        }
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("[AuthService] Erreur: Mot de passe incorrect");
            throw new RuntimeException("Invalid username or password");
        }
        
        System.out.println("[AuthService] Authentification réussie pour user ID: " + user.getId());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("[AuthService] Chargement des détails utilisateur pour: " + username);
        User user = userRepository.findByUsername(username);
        
        if (user == null) {
            System.out.println("[AuthService] Erreur: Utilisateur non trouvé pour le chargement");
            throw new UsernameNotFoundException("User not found");
        }
        
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}