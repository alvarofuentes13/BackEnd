package com.example.GameVerse_Back2.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // Constructor que inyecta JwtUtil
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Excluir rutas públicas (como login)
        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener el encabezado de autorización
        String authorizationHeader = request.getHeader("Authorization");

        // Verificar si el encabezado contiene un token JWT
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extraer el token

            // Validar el token
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token); // Extraer el email del token
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        email, null, Collections.emptyList() // Crear objeto de autenticación
                );
                SecurityContextHolder.getContext().setAuthentication(authentication); // Establecer autenticación en el contexto de seguridad
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
