package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.dto.LoginRequest;
import com.example.GameVerse_Back2.models.Usuario;
import com.example.GameVerse_Back2.repositories.UsuarioRepository;
import com.example.GameVerse_Back2.security.JwtUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return usuarioRepository.findByEmail(loginRequest.getEmail())
                .map(usuario -> {
                    if (Objects.equals(loginRequest.getPassword(), usuario.getPassword())) {
                        String token = jwtUtil.generateToken(usuario.getEmail());

                        usuario.setPassword(null); // no enviar la contraseña

                        Map<String, Object> response = new HashMap<>();
                        response.put("usuario", usuario);
                        response.put("token", token);

                        return ResponseEntity.ok(response);
                    } else {
                        return ResponseEntity.status(401).body("Contraseña incorrecta");
                    }
                })
                .orElse(ResponseEntity.status(401).body("Usuario no encontrado"));
    }


}