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
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviewDTOs = reviewService.findAll().stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviewDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        Review review = reviewService.findById(id);
        return review != null ? ResponseEntity.ok(new ReviewDTO(review)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody Review review) {
        Review nuevaReview = reviewService.save(review);
        return ResponseEntity.ok(new ReviewDTO(nuevaReview));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUsuarioId(@PathVariable Long id) {
        List<ReviewDTO> reviews = reviewService.getReviewsByUsuarioId(id)
                .stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/videojuego/{id}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByVideojuegoId(@PathVariable Long id) {
        Videojuego videojuego = videojuegoService.getOrCreateByApiId(id);
        List<ReviewDTO> reviews = reviewService.getReviewsByVideojuego(videojuego)
                .stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }
}
