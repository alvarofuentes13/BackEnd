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
    private ReviewRepository reviewRepository;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getReviewsByUsuarioId(Long id) {
        return reviewRepository.findByUsuarioId(id);
    };

    public List<Review> getReviewsByVideojuego(Videojuego videojuego) {
        return reviewRepository.findByVideojuego(videojuego);
    }

}
