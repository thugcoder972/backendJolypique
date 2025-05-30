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

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String password, String email) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setType("user");

        return userRepository.save(user);
    }

    public User authenticateAndGetUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}