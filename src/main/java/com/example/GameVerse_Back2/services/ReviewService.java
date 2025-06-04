package com.example.GameVerse_Back2.services;

import com.example.GameVerse_Back2.models.Review;
import com.example.GameVerse_Back2.models.Usuario;
import com.example.GameVerse_Back2.models.Videojuego;
import com.example.GameVerse_Back2.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository; // Repositorio para gestionar reseñas

    // Método para obtener todas las reseñas
    public List<Review> findAll() {
        return reviewRepository.findAll(); // Devuelve todas las reseñas
    }

    // Método para encontrar una reseña por su ID
    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null); // Devuelve la reseña si existe, o null
    }

    // Método para guardar una nueva reseña
    public Review save(Review review) {
        return reviewRepository.save(review); // Guarda la reseña en la base de datos
    }

    // Método para eliminar una reseña por su ID
    public void deleteById(Long id) {
        reviewRepository.deleteById(id); // Elimina la reseña de la base de datos
    }

    // Método para obtener reseñas por ID de usuario
    public List<Review> getReviewsByUsuarioId(Long id) {
        return reviewRepository.findByUsuarioId(id); // Devuelve las reseñas asociadas a un usuario específico
    }

    // Método para obtener reseñas por videojuego
    public List<Review> getReviewsByVideojuego(Videojuego videojuego) {
        return reviewRepository.findByVideojuego(videojuego); // Devuelve las reseñas asociadas a un videojuego específico
    }
}
