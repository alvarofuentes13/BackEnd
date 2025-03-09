package com.example.GameVerse_Back2.repositories;

import com.example.GameVerse_Back2.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
