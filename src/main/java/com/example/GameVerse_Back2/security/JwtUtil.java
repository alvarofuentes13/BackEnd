package com.example.GameVerse_Back2.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret; // Clave secreta para firmar el JWT

    @Value("${jwt.expiration}")
    private int jwtExpirationMs; // Tiempo de expiración del JWT en milisegundos

    private SecretKey key; // Clave secreta utilizada para la firma

    // Inicializa la clave después de que la clase es instanciada y la jwtSecret es inyectada
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Método para obtener la clave de firma
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Método para generar un token JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Establecer el sujeto (email)
                .setIssuedAt(new Date()) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // Fecha de expiración
                .signWith(getSigningKey()) // Firmar con la clave secreta
                .compact(); // Compactar el JWT en una cadena
    }

    // Método para validar el token JWT
    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build();
            parser.parseClaimsJws(token); // Analizar el token
            return true; // Si no se lanza excepción, el token es válido
        } catch (Exception e) {
            return false; // Si hay una excepción, el token no es válido
        }
    }

    // Método para extraer el email del token JWT
    public String extractEmail(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build();
        Claims claims = parser.parseClaimsJws(token).getBody(); // Obtener los reclamos del token
        return claims.getSubject(); // Retornar el sujeto (email)
    }
}
