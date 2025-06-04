package com.example.GameVerse_Back2.repositories;

import com.example.GameVerse_Back2.models.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Interfaz que define el repositorio para la entidad Videojuego
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {
    // No se han definido métodos personalizados, pero se heredan los métodos CRUD de JpaRepository
}
