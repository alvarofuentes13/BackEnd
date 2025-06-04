package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.models.Usuario;
import com.example.GameVerse_Back2.repositories.UsuarioRepository;
import com.example.GameVerse_Back2.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")  // Permite solicitudes desde cualquier origen
@RestController  // Indica que esta clase es un controlador REST
@RequestMapping("/usuarios")  // Define la ruta base para las solicitudes relacionadas con usuarios
public class UsuarioController {

    @Autowired  // Inyección automática del servicio de usuarios
    private UsuarioService usuarioService;

    @Autowired  // Inyección automática del repositorio de usuarios
    private UsuarioRepository usuarioRepository;

    // Constructor para inyección de UsuarioService
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Método para registrar un nuevo usuario
    @PostMapping  // Mapeo para solicitudes POST a /usuarios
    public ResponseEntity<Usuario> registerUser(@RequestBody Usuario usuario) {
        Usuario newUser = usuarioService.registerUser(usuario);  // Registra el nuevo usuario
        return ResponseEntity.ok(newUser);  // Retorna el usuario creado
    }

    // Método para actualizar un usuario existente
    @PutMapping("/id/{id}")  // Mapeo para solicitudes PUT a /usuarios/id/{id}
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioService.findById(id);  // Busca el usuario por ID
        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();  // Retorna 404 si no existe
        }

        // Actualiza solo los campos permitidos
        usuarioExistente.setAvatar(usuarioActualizado.getAvatar());
        usuarioExistente.setName(usuarioActualizado.getName());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        usuarioExistente.setBiografia(usuarioActualizado.getBiografia());

        Usuario actualizado = usuarioService.updateUser(usuarioExistente);  // Guarda el usuario actualizado
        return ResponseEntity.ok(actualizado);  // Retorna el usuario actualizado
    }

    // Método para obtener todos los usuarios
    @GetMapping  // Mapeo para solicitudes GET a /usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();  // Retorna la lista de todos los usuarios
    }

    // Método para obtener un usuario por su ID
    @GetMapping("/id/{id}")  // Mapeo para solicitudes GET a /usuarios/id/{id}
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);  // Busca el usuario por ID
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();  // Retorna el usuario o 404 si no existe
    }

    // Método para obtener un usuario por su email
    @GetMapping("/email/{email}")  // Mapeo para solicitudes GET a /usuarios/email/{email}
    public ResponseEntity<Optional<Usuario>> getUsuarioByEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.getUsuarioByEmail(email);  // Busca el usuario por email
        return usuario.isPresent() ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();  // Retorna el usuario o 404 si no existe
    }

    // Método para eliminar un usuario por su ID
    @DeleteMapping("/delete/{id}")  // Mapeo para solicitudes DELETE a /usuarios/delete/{id}
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);  // Elimina el usuario por ID
        return ResponseEntity.noContent().build();  // Retorna respuesta 204 No Content
    }

    // Método para seguir a otro usuario
    @PutMapping("/{seguidorId}/seguir/{seguidoId}")  // Mapeo para solicitudes PUT a /usuarios/{seguidorId}/seguir/{seguidoId}
    public ResponseEntity<?> seguirUsuario(@PathVariable Long seguidorId, @PathVariable Long seguidoId) {
        usuarioService.seguirUsuario(seguidorId, seguidoId);  // Lógica para seguir a otro usuario
        return ResponseEntity.ok("Usuario seguido con éxito");  // Retorna mensaje de éxito
    }

    // Método para dejar de seguir a otro usuario
    @PutMapping("/{seguidorId}/dejar-de-seguir/{seguidoId}")  // Mapeo para solicitudes PUT a /usuarios/{seguidorId}/dejar-de-seguir/{seguidoId}
    public ResponseEntity<?> dejarDeSeguirUsuario(@PathVariable Long seguidorId, @PathVariable Long seguidoId) {
        usuarioService.dejarDeSeguirUsuario(seguidorId, seguidoId);  // Lógica para dejar de seguir a otro usuario
        return ResponseEntity.ok("Usuario dejado de seguir con éxito");  // Retorna mensaje de éxito
    }

    // Método para verificar si un usuario está siguiendo a otro
    @GetMapping("/{seguidorId}/sigue-a/{seguidoId}")  // Mapeo para solicitudes GET a /usuarios/{seguidorId}/sigue-a/{seguidoId}
    public ResponseEntity<Boolean> estaSiguiendo(@PathVariable Long seguidorId, @PathVariable Long seguidoId) {
        boolean siguiendo = usuarioService.estaSiguiendo(seguidorId, seguidoId);  // Verifica si está siguiendo
        return ResponseEntity.ok(siguiendo);  // Retorna el resultado
    }

    // Método para obtener el perfil de un usuario
    @GetMapping("/perfil/{id}")  // Mapeo para solicitudes GET a /usuarios/perfil/{id}
    public ResponseEntity<?> getPerfilUsuario(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);  // Busca el usuario por ID

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");  // Retorna 404 si no existe
        }

        Usuario usuario = usuarioOptional.get();  // Obtiene el usuario

        // Cuenta seguidores y seguidos
        int seguidores = usuarioRepository.countSeguidores(id);
        int seguidos = usuarioRepository.countSeguidos(id);

        // Crea un mapa de respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("usuario", usuario);
        response.put("seguidores", seguidores);
        response.put("seguidos", seguidos);

        return ResponseEntity.ok(response);  // Retorna la respuesta
    }

    // Método para contar cuántos usuarios sigue un usuario
    @GetMapping("/count_seguidos/{id}")  // Mapeo para solicitudes GET a /usuarios/count_seguidos/{id}
    public ResponseEntity<?> getCountSeguidos(@PathVariable Long id) {
        int seguidos = usuarioRepository.countSeguidos(id);  // Cuenta los seguidos

        if (seguidos < 1) {
            return ResponseEntity.ok(0);  // Retorna 0 si no sigue a nadie
        }

        return ResponseEntity.ok(seguidos);  // Retorna la cantidad de seguidos
    }

    // Método para contar cuántos seguidores tiene un usuario
    @GetMapping("/count_seguidores/{id}")  // Mapeo para solicitudes GET a /usuarios/count_seguidores/{id}
    public ResponseEntity<?> getCountSeguidores(@PathVariable Long id) {
        int seguidores = usuarioRepository.countSeguidores(id);  // Cuenta los seguidores

        if (seguidores < 1) {
            return ResponseEntity.ok(0);  // Retorna 0 si no tiene seguidores
        }

        return ResponseEntity.ok(seguidores);  // Retorna la cantidad de seguidores
    }
}
