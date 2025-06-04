package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.dto.ReviewDTO;
import com.example.GameVerse_Back2.models.Review;
import com.example.GameVerse_Back2.models.Videojuego;
import com.example.GameVerse_Back2.repositories.ReviewRepository;
import com.example.GameVerse_Back2.services.ReviewService;
import com.example.GameVerse_Back2.services.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")  // Permite solicitudes desde cualquier origen
@RestController  // Indica que esta clase es un controlador REST
@RequestMapping("/reviews")  // Define la ruta base para las solicitudes relacionadas con reseñas
public class ReviewController {

    @Autowired  // Inyección automática del servicio de reseñas
    private ReviewService reviewService;

    @Autowired  // Inyección automática del servicio de videojuegos
    private VideojuegoService videojuegoService;

    @Autowired  // Inyección automática del repositorio de reseñas
    private ReviewRepository reviewRepository;

    // Método para obtener todas las reseñas
    @GetMapping  // Mapeo para solicitudes GET a /reviews
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        // Convierte todas las reseñas a DTOs y las retorna
        List<ReviewDTO> reviewDTOs = reviewService.findAll().stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviewDTOs);  // Retorna respuesta 200 OK con las reseñas
    }

    // Método para obtener una reseña por su ID
    @GetMapping("/{id}")  // Mapeo para solicitudes GET a /reviews/{id}
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        Review review = reviewService.findById(id);  // Busca la reseña por ID
        return review != null ? ResponseEntity.ok(new ReviewDTO(review)) : ResponseEntity.notFound().build();  // Retorna la reseña o 404 si no existe
    }

    // Método para crear una nueva reseña
    @PostMapping  // Mapeo para solicitudes POST a /reviews
    public ResponseEntity<ReviewDTO> createReview(@RequestBody Review review) {
        Review nuevaReview = reviewService.save(review);  // Guarda la nueva reseña
        return ResponseEntity.ok(new ReviewDTO(nuevaReview));  // Retorna la nueva reseña como DTO
    }

    // Método para eliminar una reseña por su ID
    @DeleteMapping("/{id}")  // Mapeo para solicitudes DELETE a /reviews/{id}
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);  // Elimina la reseña por ID
        return ResponseEntity.noContent().build();  // Retorna respuesta 204 No Content
    }

    // Método para obtener reseñas por ID de usuario
    @GetMapping("/usuario/{id}")  // Mapeo para solicitudes GET a /reviews/usuario/{id}
    public ResponseEntity<List<ReviewDTO>> getReviewsByUsuarioId(@PathVariable Long id) {
        // Convierte las reseñas encontradas a DTOs y las retorna
        List<ReviewDTO> reviews = reviewService.getReviewsByUsuarioId(id)
                .stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);  // Retorna respuesta 200 OK con las reseñas
    }

    // Método para obtener reseñas por ID de videojuego
    @GetMapping("/videojuego/{id}")  // Mapeo para solicitudes GET a /reviews/videojuego/{id}
    public ResponseEntity<List<ReviewDTO>> getReviewsByVideojuegoId(@PathVariable Long id) {
        Videojuego videojuego = videojuegoService.getOrCreateByApiId(id);  // Obtiene o crea el videojuego por su ID de API
        // Convierte las reseñas encontradas a DTOs y las retorna
        List<ReviewDTO> reviews = reviewService.getReviewsByVideojuego(videojuego)
                .stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);  // Retorna respuesta 200 OK con las reseñas
    }
}
