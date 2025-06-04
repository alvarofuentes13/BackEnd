package com.example.GameVerse_Back2.services;

import com.example.GameVerse_Back2.models.Usuario;
import com.example.GameVerse_Back2.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositorio para gestionar usuarios

    @Autowired
    private PasswordEncoder passwordEncoder; // Codificador de contraseñas

    // Método para obtener un usuario por su email
    public Optional<Usuario> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email); // Devuelve el usuario si existe
    }

    // Método para obtener todos los usuarios
    public List<Usuario> findAll() {
        return usuarioRepository.findAll(); // Devuelve todos los usuarios
    }

    // Método para encontrar un usuario por su ID
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null); // Devuelve el usuario si existe, o null
    }

    // Método para seguir a otro usuario
    public void seguirUsuario(Long seguidorId, Long seguidoId) {
        Usuario seguidor = usuarioRepository.findById(seguidorId)
                .orElseThrow(() -> new RuntimeException("Seguidor no encontrado"));

        Usuario seguido = usuarioRepository.findById(seguidoId)
                .orElseThrow(() -> new RuntimeException("Usuario a seguir no encontrado"));

        seguido.getSeguidores().add(seguidor); // Agrega el seguidor a la lista de seguidores del usuario seguido
        usuarioRepository.save(seguido); // Guarda los cambios
    }

    // Método para dejar de seguir a otro usuario
    public void dejarDeSeguirUsuario(Long seguidorId, Long seguidoId) {
        Usuario seguidor = usuarioRepository.findById(seguidorId)
                .orElseThrow(() -> new RuntimeException("Seguidor no encontrado"));

        Usuario seguido = usuarioRepository.findById(seguidoId)
                .orElseThrow(() -> new RuntimeException("Usuario a dejar de seguir no encontrado"));

        seguido.getSeguidores().remove(seguidor); // Elimina el seguidor de la lista de seguidores
        usuarioRepository.save(seguido); // Guarda los cambios
    }

    // Método para verificar si un usuario sigue a otro
    public boolean estaSiguiendo(Long seguidorId, Long seguidoId) {
        Usuario seguido = usuarioRepository.findById(seguidoId)
                .orElseThrow(() -> new RuntimeException("Usuario seguido no encontrado"));
        return seguido.getSeguidores()
                .stream()
                .anyMatch(u -> u.getId().equals(seguidorId)); // Devuelve true si el seguidor está en la lista
    }

    // Método para eliminar un usuario por su ID
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id); // Elimina el usuario de la base de datos
    }

    // Método para registrar un nuevo usuario
    public Usuario registerUser(Usuario usuario) {
        String hashedPassword = passwordEncoder.encode(usuario.getPassword()); // Codifica la contraseña
        usuario.setPassword(hashedPassword); // Establece la contraseña codificada
        return usuarioRepository.save(usuario); // Guarda el nuevo usuario
    }

    // Método para actualizar un usuario existente
    public Usuario updateUser(Usuario usuario) {
        return usuarioRepository.save(usuario); // Guarda los cambios en el usuario
    }
}
