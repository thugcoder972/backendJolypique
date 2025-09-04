// package com.mysycorp.Backendjo.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;

// @Configuration
// public class CorsConfig {

//     @Bean
//     public CorsFilter corsFilter() {
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         CorsConfiguration config = new CorsConfiguration();
        
//         // Autorisez votre domaine frontend
//         config.addAllowedOrigin("http://localhost:3000");
//         // Autorisez les credentials
//         config.setAllowCredentials(true);
//         // Autorisez les headers
//         config.addAllowedHeader("*");
//         // Autorisez les méthodes
//         config.addAllowedMethod("*");
        
//         source.registerCorsConfiguration("/**", config);
//         return new CorsFilter(source);
//     }
// }
    package com.mysycorp.Backendjo.config;


// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.*;

// @Configuration
// public class CorsConfig implements WebMvcConfigurer {

//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//             .allowedOrigins("http://localhost:3000")
//             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//             .allowedHeaders("*")
//             .allowCredentials(true)
//             .maxAge(3600);
//     }
// }

