package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.dto.LoginRequest;
import com.example.GameVerse_Back2.dto.RegisterRequest;
import com.example.GameVerse_Back2.dto.UsuarioDTO;
import com.example.GameVerse_Back2.models.Usuario;
import com.example.GameVerse_Back2.repositories.UsuarioRepository;
import com.example.GameVerse_Back2.security.JwtUtil;
import com.example.GameVerse_Back2.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController  // Indica que esta clase es un controlador REST
@CrossOrigin("*")  // Permite solicitudes desde cualquier origen
@RequestMapping("/auth")  // Define la ruta base para las solicitudes de autenticación
public class AuthController {

    @Autowired  // Inyección automática del repositorio de usuarios
    private UsuarioRepository usuarioRepository;

    @Autowired  // Inyección automática del servicio de usuario
    private UsuarioService usuarioService;

    @Autowired  // Inyección automática del codificador de contraseñas
    private PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;  // Utilidad para manejar tokens JWT

    // Constructor que inyecta la utilidad de JWT
    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Método para manejar la solicitud de inicio de sesión
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Busca el usuario por email
        return usuarioRepository.findByEmail(loginRequest.getEmail())
                .map(usuario -> {
                    // Verifica si la contraseña coincide
                    if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                        String token = jwtUtil.generateToken(usuario.getEmail());  // Genera un token JWT

                        usuario.setPassword(null); // No enviar la contraseña en la respuesta

                        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);  // Convierte a DTO para la respuesta

                        Map<String, Object> response = new HashMap<>();  // Crea un mapa para la respuesta
                        response.put("usuario", usuarioDTO);  // Agrega el usuario
                        response.put("token", token);  // Agrega el token

                        return ResponseEntity.ok(response);  // Retorna respuesta 200 OK
                    } else {
                        return ResponseEntity.status(401).body("Contraseña incorrecta");  // Retorna 401 si la contraseña es incorrecta
                    }
                })
                .orElse(ResponseEntity.status(402).body("Usuario no encontrado"));  // Retorna 402 si el usuario no existe
    }

    // Método para manejar la solicitud de registro
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Validar si el email ya está en uso
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body("El correo ya está en uso");  // Retorna 400 si el correo ya existe
        }

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setName(request.getName());  // Establece el nombre
        nuevoUsuario.setEmail(request.getEmail());  // Establece el email
        nuevoUsuario.setPassword(request.getPassword()); // Establece la contraseña (recomendado: cifrar)
        nuevoUsuario.setBiografia("Nuevo usuario en GameVerse");  // Establece biografía por defecto
        nuevoUsuario.setFechaRegistro(LocalDateTime.now());  // Establece la fecha de registro
        nuevoUsuario.setAvatar(null); // Establece el avatar (puede ser un valor por defecto)

        Usuario saved = usuarioService.registerUser(nuevoUsuario);  // Registra el nuevo usuario

        // Generar token JWT
        String token = jwtUtil.generateToken(saved.getEmail());

        saved.setPassword(null); // No enviar la contraseña en la respuesta

        Map<String, Object> response = new HashMap<>();  // Crea un mapa para la respuesta
        response.put("usuario", saved);  // Agrega el usuario registrado
        response.put("token", token);  // Agrega el token

        return ResponseEntity.status(201).body(response);  // Retorna respuesta 201 Created
    }
}
