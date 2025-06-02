package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.dto.LoginRequest;
import com.example.GameVerse_Back2.dto.RegisterRequest;
import com.example.GameVerse_Back2.dto.UsuarioDTO;
import com.example.GameVerse_Back2.models.Usuario;
import com.example.GameVerse_Back2.repositories.UsuarioRepository;
import com.example.GameVerse_Back2.security.JwtUtil;
import com.example.GameVerse_Back2.services.UsuarioService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return usuarioRepository.findByEmail(loginRequest.getEmail())
                .map(usuario -> {
                    if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                        String token = jwtUtil.generateToken(usuario.getEmail());

                        usuario.setPassword(null); // no enviar la contraseña

                        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);

                        Map<String, Object> response = new HashMap<>();
                        response.put("usuario", usuarioDTO);
                        response.put("token", token);

                        return ResponseEntity.ok(response);
                    } else {
                        return ResponseEntity.status(401).body("Contraseña incorrecta");
                    }
                })
                .orElse(ResponseEntity.status(402).body("Usuario no encontrado"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Validar si el email ya está en uso
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body("El correo ya está en uso");
        }

        // Crear usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setName(request.getName());
        nuevoUsuario.setEmail(request.getEmail());
        nuevoUsuario.setPassword(request.getPassword()); // Recomendado: cifrar
        nuevoUsuario.setBiografia("Nuevo usuario en GameVerse");
        nuevoUsuario.setFechaRegistro(LocalDateTime.now());
        nuevoUsuario.setAvatar(null); // o un valor por defecto

        Usuario saved = usuarioService.registerUser(nuevoUsuario);

        // Generar token
        String token = jwtUtil.generateToken(saved.getEmail());

        saved.setPassword(null); // no enviar la contraseña

        Map<String, Object> response = new HashMap<>();
        response.put("usuario", saved);
        response.put("token", token);

        return ResponseEntity.status(201).body(response);
    }


}