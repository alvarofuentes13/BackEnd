package com.example.GameVerse_Back2.services;

import com.example.GameVerse_Back2.models.Videojuego;
import com.example.GameVerse_Back2.repositories.VideojuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideojuegoService {

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    public List<Videojuego> findAll() {
        return videojuegoRepository.findAll();
    }

    public Videojuego findById(Long id) {
        return videojuegoRepository.findById(id).orElse(null);
    }

    public Videojuego save(Videojuego videojuego) {
        return videojuegoRepository.save(videojuego);
    }

    public void deleteById(Long id) {
        videojuegoRepository.deleteById(id);
    }
}
