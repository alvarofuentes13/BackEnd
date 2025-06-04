package com.example.GameVerse_Back2.repositories;

import com.example.GameVerse_Back2.models.Review;
import com.example.GameVerse_Back2.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT COUNT(u) FROM Usuario u JOIN u.seguidores s WHERE s.id = :usuarioId")
    int countSeguidos(@Param("usuarioId") Long usuarioId);

    @Query("SELECT COUNT(s) FROM Usuario u JOIN u.seguidores s WHERE u.id = :usuarioId")
    int countSeguidores(@Param("usuarioId") Long usuarioId);
}
