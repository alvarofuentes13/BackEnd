package com.example.GameVerse_Back2.services;

import com.example.GameVerse_Back2.models.Usuario;
import com.example.GameVerse_Back2.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    

    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }


    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario registerUser(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

//    public boolean validatePassword(String rawPassword, String encryptedPassword) {
//        return passwordEncoder.matches(rawPassword, encryptedPassword);
//    }
//
//    public Usuario validarCredenciales(String email, String password) {
//        Usuario usuario = usuarioRepository.findByEmail(email);
//        if (usuario == null) {
//            return null; // No existe el usuario
//        }
//
//        // Comparar la contraseña ingresada con la almacenada (encriptada)
//        if (passwordEncoder.matches(password, usuario.getPassword())) return usuario;
//        return null;
//    }
}
