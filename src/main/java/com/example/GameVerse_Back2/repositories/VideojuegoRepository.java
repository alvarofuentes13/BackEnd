package com.example.GameVerse_Back2.repositories;

import com.example.GameVerse_Back2.models.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {
}
