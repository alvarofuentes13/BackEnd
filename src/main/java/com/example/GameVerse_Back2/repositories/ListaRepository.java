package com.example.GameVerse_Back2.repositories;

import com.example.GameVerse_Back2.models.Lista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListaRepository extends JpaRepository<Lista, Long> {
    List<Lista> findByNombreContainingIgnoreCase(String nombre);
    List<Lista> findByUsuarioId(Long usuarioId);
}
