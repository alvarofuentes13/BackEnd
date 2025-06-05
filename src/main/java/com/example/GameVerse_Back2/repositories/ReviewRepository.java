package com.example.GameVerse_Back2.repositories;

import com.example.GameVerse_Back2.models.Review;
import com.example.GameVerse_Back2.models.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Interfaz que define el repositorio para la entidad Review
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Método para buscar reseñas por ID de usuario
    List<Review> findByUsuarioId(Long usuarioId);

    // Método para buscar reseñas asociadas a un videojuego específico
    List<Review> findByVideojuego(Videojuego videojuego);
}
