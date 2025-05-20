package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.models.Review;
import com.example.GameVerse_Back2.models.Videojuego;
import com.example.GameVerse_Back2.repositories.ReviewRepository;
import com.example.GameVerse_Back2.services.ReviewService;
import com.example.GameVerse_Back2.services.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private VideojuegoService videojuegoService;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Review review = reviewService.findById(id);
        return review != null ? ResponseEntity.ok(review) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review nuevaReview = reviewService.save(review);
        return ResponseEntity.ok(nuevaReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Review>> getReviewsByUsuarioId(@PathVariable Long id) {
        List<Review> reviews = reviewService.getReviewsByUsuarioId(id);

        return reviews.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(reviews);
    }

    @GetMapping("/videojuego/{id}")
    public ResponseEntity<List<Review>> getReviewsByVideojuegoId(@PathVariable Long id) {
        // Buscar o crear el videojuego usando la API externa si no existe
        Videojuego videojuego = videojuegoService.getOrCreateByApiId(id);

        // Obtener reviews asociadas a ese videojuego
        List<Review> reviews = reviewService.getReviewsByVideojuego(videojuego);

        return ResponseEntity.ok(reviews); // Devuelve lista vacía si no hay reseñas
    }


}
