// package com.mysycorp.Backendjo.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {
//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//                 .allowedOrigins("http://localhost:3000") // l'URL de votre front-end React
//                 .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                 .allowedHeaders("*")
//                 .allowCredentials(true);
                
//     }
// }
package com.mysycorp.Backendjo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // tous les endpoints
                .allowedOrigins(
                    "http://localhost:3000",          // front dev
                    "https://thugcoder972.github.io"  // front prod
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // méthodes autorisées
                .allowedHeaders("*")           // tous les headers
                .allowCredentials(true)        // cookies / auth
                .maxAge(3600);                 // durée de cache pré-vol (1h)
    }
}
