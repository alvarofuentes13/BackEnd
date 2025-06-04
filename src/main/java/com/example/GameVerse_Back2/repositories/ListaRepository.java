package com.example.GameVerse_Back2.repositories;

import com.example.GameVerse_Back2.models.Lista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Interfaz que define el repositorio para la entidad Lista
public interface ListaRepository extends JpaRepository<Lista, Long> {

    // Método para buscar listas por nombre (contiene la cadena dada, sin importar mayúsculas o minúsculas)
    List<Lista> findByNombreContainingIgnoreCase(String nombre);

    // Método para buscar listas por ID de usuario
    List<Lista> findByUsuarioId(Long usuarioId);
}
