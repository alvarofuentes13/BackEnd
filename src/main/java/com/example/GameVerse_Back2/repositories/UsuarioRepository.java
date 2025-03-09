package com.example.GameVerse_Back2.repositories;

import com.example.GameVerse_Back2.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
