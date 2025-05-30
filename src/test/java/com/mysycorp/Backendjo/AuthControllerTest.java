
package com.mysycorp.Backendjo;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;


class AuthControllerTest {

  @Test
void login_ValidCredentials_ReturnsTokenAndUserId() {
    com.mysycorp.Backendjo.entity.User mockUser = 
        new com.mysycorp.Backendjo.entity.User();
    mockUser.setUsername("testuser");
    mockUser.setId(27L);
    
    org.springframework.security.core.userdetails.User userDetails = 
        (User) org.springframework.security.core.userdetails.User.builder()
        .username("testuser")
        .password("encodedPassword")
        .roles("USER")
        .build();
    
}
}