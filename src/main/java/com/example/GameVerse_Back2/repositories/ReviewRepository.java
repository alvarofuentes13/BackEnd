package com.example.GameVerse_Back2.repositories;

import com.example.GameVerse_Back2.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUsuarioId(Long usuarioId);

    List<Review> findByVideojuegoId(Long videojuegoid);
}
