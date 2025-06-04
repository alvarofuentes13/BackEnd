package com.example.GameVerse_Back2.config;

import com.example.GameVerse_Back2.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration  // Indica que esta clase es una fuente de configuración para Spring
public class SecurityConfig {

    private final JwtFilter jwtFilter;  // Filtro JWT para manejar la autenticación

    // Constructor que inyecta el filtro JWT
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean  // Define un bean para la configuración de CORS
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();  // Crea una nueva configuración de CORS
        config.setAllowedOrigins(List.of("http://localhost:8082")); // Permite el origen del frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP permitidos
        config.setAllowedHeaders(List.of("*"));  // Permite todos los encabezados
        config.setAllowCredentials(true); // Permite el uso de cookies y encabezados de autorización

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // Fuente de configuración basada en URL
        source.registerCorsConfiguration("/**", config); // Registra la configuración de CORS para todas las rutas
        return source;  // Retorna la fuente de configuración
    }

    @Bean  // Define un bean para la cadena de filtros de seguridad
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())  // Desactiva la protección CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilita CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/igdb/**").permitAll()  // Permite acceso sin token a autenticación e IGDB
                        .anyRequest().authenticated())                // Requiere autenticación para cualquier otra solicitud
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Agrega el filtro JWT antes del filtro de autenticación
                .build();  // Construye la cadena de filtros
    }

    @Bean  // Define un bean para el administrador de autenticación
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Retorna el administrador de autenticación
    }

    @Bean  // Define un bean para el codificador de contraseñas
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // Retorna un codificador de contraseñas usando BCrypt
    }
}

